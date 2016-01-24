import scala.swing._
import scala.swing.BorderPanel.Position._
import javax.swing.ImageIcon
import event._

import de.htwg.core.GameCoreController
import de.htwg.core.entities._



object BJGui extends SimpleSwingApplication {

  def top = new MainFrame {
    val game = GameCoreController.startNewGame
    var round = GameCoreController.startNewRound(game)

    title = "Blackjack"
    // TODO in future: relative size to screen size
    preferredSize = new Dimension(1000,768)

    // Referenzen zu restlichen GUI Komponenten
    val bnGiveCard = new Button {
      text = "Hit me!"
      enabled = false
    }
    val bnStand = new Button {
      text = "Stand"
      enabled = false
      // TODO styling background = java.awt.Color.magenta
    }
    val lblStake = new Label {
      text = "Stake in $: "
    }
    val txtStake = txtField
    txtStake.enabled = false
    val bnNewRound = new Button {
      text = "New Round!"
    }
    val lblMoney = new Label {
      text = "Money: " + round.humanRoundPlayer.player.money.toString
    }
    val lblCenterDefault = new Label {
      icon = new ImageIcon(getClass.getResource("blackjack.png"))
    }
    // Methode um Referenz auf ein EditText zu bekommen
    def txtField = new TextField {
      horizontalAlignment = Alignment.Left
    }

    // fülle untergeordnete Layouts
    val gridBagPanelE = new GridBagPanel {
      val c = new Constraints

      c.gridx = 0
      c.gridy = 0
      c.ipadx= 75
      layout(bnGiveCard) = c

      c.gridx = 0
      c.gridy = 1
      c.ipadx= 75
      layout(bnStand) = c
    }

    val gridBagPanelW = new GridBagPanel {

      val c = new Constraints

      c.gridx = 0
      c.gridy = 0
      layout(lblStake) = c

      c.gridx = 1
      c.gridy = 0
      c.ipadx = 75
      layout(txtStake) = c

      c.gridx = 0
      c.gridy = 2
      c.gridwidth = 2
      layout(bnNewRound) = c

      c.gridx = 0
      c.gridy = 3
      layout(lblMoney) = c
    }

    val flowPanelS = new FlowPanel
    val flowPanelN = new FlowPanel
    // setze primäres Layout
    contents = new BorderPanel {
      layout(gridBagPanelW) = West
      layout(lblCenterDefault) = Center
      layout(flowPanelN) = North
      layout(flowPanelS) = South
      layout(gridBagPanelE) = East
    }

    // setze Listener
    listenTo(bnGiveCard)
    listenTo(bnStand)
    listenTo(txtStake.keys)
    listenTo(bnNewRound)

    // setze reactions 
    reactions += {
      case ButtonClicked(component) if component == bnGiveCard =>
        round = GameCoreController.hit(round)
        updateCards

      case ButtonClicked(component) if component == bnStand =>
        bnGiveCard.enabled = false
        bnStand.enabled = false
        bnNewRound.enabled = true

        // ermittle den Gewinner und verändere dessen Geld + verändere Center Bild
        // TODO show dealer cards
        round = GameCoreController.finish(round)
        if (round.humanRoundPlayer.winner) {
          lblCenterDefault.icon = new ImageIcon(getClass.getResource("winner.jpg"))
        } else {
          lblCenterDefault.icon = new ImageIcon(getClass.getResource("lost.jpg"))
        }
        // TODO get right amount of money after round
        lblMoney.text = "Money: " + round.humanRoundPlayer.player.money.toString

        // starte neue Runde
        round = GameCoreController.startNewRound(game)

      case ButtonClicked(component) if component == bnNewRound =>
        startNewRound

      case KeyPressed(_, Key.Enter, _, _) =>
        if(!(txtStake.text.forall { _.isDigit })) {
          Dialog.showMessage(new FlowPanel, "Please use numbers only!")
          txtStake.text = ""
        } else {
          round = GameCoreController.bet(round,(txtStake.text).toInt)
          txtStake.enabled = false
          bnGiveCard.enabled = true
          bnStand.enabled = true
        }
    }

    def startNewRound = {
      // initialisiere Spielfeld
      lblCenterDefault.icon = new ImageIcon(getClass.getResource("blackjack.png"))
      bnGiveCard.enabled = false
      bnStand.enabled = false
      txtStake.enabled = true
      txtStake.text = ""
      bnNewRound.enabled = false
      updateCards
    }

    def updateCards = {
      def getScaledImageLabel(card: Card):Label = {

        def getColor(color:String):String = color match {
          case "Kreuz" => "clubs"
          case "Pik" => "spade"
          case "Herz" => "heart"
          case "Karo" => "diamond"
          case "unknown" => "back"
        }

        def getNumber(number:String): String = number match {
          case "A" => "ace"
          case "J" => "jack"
          case "Q" => "queen"
          case "K" => "king"
          case "unknown" => ""
          case _ => number
        }

        // Image Größe ändern und als Icon setzen
        val imageFilePath:String = getColor(card.color) + "_" + getNumber(card.number) + ".png"
        val ic = new ImageIcon(getClass.getResource(imageFilePath))
        // TODO in future: relative size to screen size
        val img:Image = ic.getImage.getScaledInstance(150,-1,0)
        ic.setImage(img)

        val label = new Label {
          icon = ic
        }

        return label
      }

      // fülle den Spielerpanel mit seinen Karten
      val cards:List[Card] = round.humanRoundPlayer.hand.visibleCards
      flowPanelS.contents.clear()

      for (card<-cards) {
        flowPanelS.contents += getScaledImageLabel(card)
      }
      flowPanelS.revalidate()
      flowPanelS.repaint()

      // fülle den Dealerpanel mit seinen Karten
      val cardsDealer:List[Card] = round.bankRoundPlayer.hand.visibleCards
      flowPanelN.contents.clear()

      for (card<-cardsDealer) {
        flowPanelN.contents += getScaledImageLabel(card)
      }
      flowPanelN.revalidate()
      flowPanelN.repaint()
    }
  }
} 