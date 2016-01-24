import scala.swing._
import scala.swing.BorderPanel.Position._
import javax.swing.ImageIcon
import event._

import de.htwg.core.GameCoreController
import de.htwg.core.entities._



object BJGui extends SimpleSwingApplication {

  def top = new MainFrame {
    var round = GameCoreController.startNewRound(GameCoreController.startNewGame)

    title = "Blackjack"
    // TODO in future: relative size to screen size
    preferredSize = new Dimension(1000,768)

    // Referenzen zu restlichen GUI Komponenten
    val bnGiveCard = new Button {
      text = "Hit me"
      enabled = false
      background = java.awt.Color.darkGray
      foreground = java.awt.Color.white
    }
    val bnStand = new Button {
      text = "Stand"
      enabled = false
      background = java.awt.Color.darkGray
      foreground = java.awt.Color.white
    }
    val lblStake = new Label {
      text = "Stake in $: "
    }
    val txtStake = txtField
    txtStake.enabled = false
    val bnNewRound = new Button {
      text = "New Round"
      background = java.awt.Color.darkGray
      foreground = java.awt.Color.white
    }
    val lblMoney = new Label {
      text = "Money :" + round.humanRoundPlayer.player.money.toString
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

      background = java.awt.Color.white
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

      background = java.awt.Color.white
    }

    val flowPanelS = new FlowPanel {
      background = java.awt.Color.white
    }
    val flowPanelN = new FlowPanel {
      background = java.awt.Color.white
    }

    // setze primäres Layout
    contents = new BorderPanel {
      layout(gridBagPanelW) = West
      layout(lblCenterDefault) = Center
      layout(flowPanelN) = North
      layout(flowPanelS) = South
      layout(gridBagPanelE) = East
      background = java.awt.Color.white
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
        updateCards(flowPanelS, round.humanRoundPlayer.hand.visibleCards)

      case ButtonClicked(component) if component == bnStand =>
        bnGiveCard.enabled = false
        bnStand.enabled = false
        bnNewRound.enabled = true

        // ermittle den Gewinner und verändere dessen Geld + verändere Center Bild
        round = GameCoreController.finish(round)
        if (round.humanRoundPlayer.winner) {
          lblCenterDefault.icon = new ImageIcon(getClass.getResource("winner.jpg"))
        } else {
          lblCenterDefault.icon = new ImageIcon(getClass.getResource("lost.jpg"))
        }
        lblMoney.text = "Money :" + round.humanRoundPlayer.player.money.toString
        updateCards(flowPanelN, round.bankRoundPlayer.hand.allCards)
        // starte neue Runde
        round = GameCoreController.startNewRound(round.game)

      case ButtonClicked(component) if component == bnNewRound =>
        startNewRound

      case KeyPressed(_, Key.Enter, _, _) =>
        if(!(txtStake.text.forall { _.isDigit })) {
          Dialog.showMessage(new FlowPanel, "Please use numbers only!")
          txtStake.text = ""
        } else {
          txtStake.enabled = false
          bnGiveCard.enabled = true
          bnStand.enabled = true
          round = GameCoreController.bet(round,(txtStake.text).toInt)
          updateCards(flowPanelS, round.humanRoundPlayer.hand.visibleCards)
          updateCards(flowPanelN, round.bankRoundPlayer.hand.visibleCards)
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
      flowPanelN.contents.clear
      flowPanelS.contents.clear
    }

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

    def updateCards(flowPanel:FlowPanel, cards:List[Card] ) : FlowPanel = {
      flowPanel.contents.clear()
      for (card<-cards) {
        flowPanel.contents += getScaledImageLabel(card)
      }
      flowPanel.revalidate()
      flowPanel.repaint()

      return flowPanel
    }

  }
} 