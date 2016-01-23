import scala.swing._
import scala.swing.BorderPanel.Position._
import javax.swing.ImageIcon
import event._

import de.htwg.core.GameCoreController
import de.htwg.core.entities._



object BJGui extends SimpleSwingApplication {

  // globale Variablen?
  var round = GameCoreController.startNewRound(GameCoreController.startNewGame)
  var flowPanelN = new FlowPanel()

  def top = new MainFrame {

    title = "Blackjack"

    // alle Kartenbilder als Labelicons
    val spade_2  = new Label {
      icon = new ImageIcon("spade_2.png")
    }
    val spade_3  = new Label {
      icon = new ImageIcon("spade_3.png")
    }
    val spade_4  = new Label {
      icon = new ImageIcon("spade_4.png")
    }
    val spade_5  = new Label {
      icon = new ImageIcon("spade_5.png")
    }
    val spade_6  = new Label {
      icon = new ImageIcon("spade_6.png")
    }
    val spade_7  = new Label {
      icon = new ImageIcon("spade_7.png")
    }
    val spade_8  = new Label {
      icon = new ImageIcon("spade_8.png")
    }
    val spade_9  = new Label {
      icon = new ImageIcon("spade_9.png")
    }
    val spade_10  = new Label {
      icon = new ImageIcon("spade_10.png")
    }
    val spade_ace  = new Label {
      icon = new ImageIcon("spade_ace.png")
    }
    val spade_jack  = new Label {
      icon = new ImageIcon("spade_jack.png")
    }
    val spade_king  = new Label {
      icon = new ImageIcon("spade_king.png")
    }
    val spade_queen  = new Label {
      icon = new ImageIcon("spade_queen.png")
    }
    val clubs_2  = new Label {
      icon = new ImageIcon("clubs_2.png")
    }
    val clubs_3  = new Label {
      icon = new ImageIcon("clubs_3.png")
    }
    val clubs_4  = new Label {
      icon = new ImageIcon("clubs_4.png")
    }
    val clubs_5  = new Label {
      icon = new ImageIcon("clubs_5.png")
    }
    val clubs_6  = new Label {
      icon = new ImageIcon("clubs_6.png")
    }
    val clubs_7  = new Label {
      icon = new ImageIcon("clubs_7.png")
    }
    val clubs_8  = new Label {
      icon = new ImageIcon("clubs_8.png")
    }
    val clubs_9  = new Label {
      icon = new ImageIcon("clubs_9.png")
    }
    val clubs_10  = new Label {
      icon = new ImageIcon("clubs_10.png")
    }
    val clubs_ace  = new Label {
      icon = new ImageIcon("clubs_ace.png")
    }
    val clubs_jack  = new Label {
      icon = new ImageIcon("clubs_jack.png")
    }
    val clubs_king  = new Label {
      icon = new ImageIcon("clubs_king.png")
    }
    val clubs_queen  = new Label {
      icon = new ImageIcon("clubs_queen.png")
    }
    val heart_2  = new Label {
      icon = new ImageIcon("heart_2.png")
    }
    val heart_3  = new Label {
      icon = new ImageIcon("heart_3.png")
    }
    val heart_4  = new Label {
      icon = new ImageIcon("heart_4.png")
    }
    val heart_5  = new Label {
      icon = new ImageIcon("heart_5.png")
    }
    val heart_6  = new Label {
      icon = new ImageIcon("heart_6.png")
    }
    val heart_7  = new Label {
      icon = new ImageIcon("heart_7.png")
    }
    val heart_8  = new Label {
      icon = new ImageIcon("heart_8.png")
    }
    val heart_9  = new Label {
      icon = new ImageIcon("heart_9.png")
    }
    val heart_10  = new Label {
      icon = new ImageIcon("heart_10.png")
    }
    val heart_ace  = new Label {
      icon = new ImageIcon("heart_ace.png")
    }
    val heart_jack  = new Label {
      icon = new ImageIcon("heart_jack.png")
    }
    val heart_king  = new Label {
      icon = new ImageIcon("heart_king.png")
    }
    val heart_queen  = new Label {
      icon = new ImageIcon("heart_queen.png")
    }
    val diamond_2  = new Label {
      icon = new ImageIcon("diamond_2.png")
    }
    val diamond_3  = new Label {
      icon = new ImageIcon("diamond_3.png")
    }
    val diamond_4  = new Label {
      icon = new ImageIcon("diamond_4.png")
    }
    val diamond_5  = new Label {
      icon = new ImageIcon("diamond_5.png")
    }
    val diamond_6  = new Label {
      icon = new ImageIcon("diamond_6.png")
    }
    val diamond_7  = new Label {
      icon = new ImageIcon("diamond_7.png")
    }
    val diamond_8  = new Label {
      icon = new ImageIcon("diamond_8.png")
    }
    val diamond_9  = new Label {
      icon = new ImageIcon("diamond_9.png")
    }
    val diamond_10  = new Label {
      icon = new ImageIcon("diamond_10.png")
    }
    val diamond_ace  = new Label {
      icon = new ImageIcon("diamond_ace.png")
    }
    val diamond_jack  = new Label {
      icon = new ImageIcon("diamond_jack.png")
    }
    val diamond_king  = new Label {
      icon = new ImageIcon("diamond_king.png")
    }
    val diamond_queen  = new Label {
      icon = new ImageIcon("diamond_queen.png")
    }

    // Referenzen zu restlichen GUI Komponenten
    val bnGiveCard = new Button {
      text = "Hit me!"
      enabled = false
    }
    val bnStand = new Button {
      text = "Stand"
      enabled = false
    }
    val lblStake = new Label {
      text = "Stake in $: "
    }
    val txtStake = txtField
    txtStake.enabled = false
    val bnNewRound = new Button {
      text = "New Round!"
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

    val lblCenterDefault = new Label {
      icon = new ImageIcon(getClass.getResource("blackjack.png"))
    }
    val lblCenterWon = new Label {
      icon = new ImageIcon("winner.jpg")
    }
    val lblCenterLost = new Label {
      icon = new ImageIcon("lost.jpg")
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
    }

    //val flowPanelN = new FlowPanel()
    val list:List[Label] = Nil
    val flowPanelS = new FlowPanel () {}

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
        round = round.hit(round.humanRoundPlayer.player)
        updateCards
      case ButtonClicked(component) if component == bnStand =>
        bnGiveCard.enabled = false
        bnStand.enabled = false
        bnNewRound.enabled = true

        round = round.finish()
        if (round.getWinners.isEmpty) {
          lblCenterDefault.icon = new ImageIcon("winner.jpg")
        } else {
          lblCenterLost.icon = new ImageIcon("lost.jpg")
        }
        // show dealer cards
      case ButtonClicked(component) if component == bnNewRound =>
        startNewRound
      case KeyPressed(_, Key.Enter, _, _) =>
        //def isAllDigits(x: String) = x forall Character.isDigit
        if(!(txtStake.text.forall { _.isDigit })) {
          Dialog.showMessage(new FlowPanel, "Please use numbers only!")
          txtStake.text = ""
        } else {
          round = round.bet(round.humanRoundPlayer.player.asInstanceOf[HumanPlayer],(txtStake.text).toInt)
          txtStake.enabled = false
          bnGiveCard.enabled = true
          bnStand.enabled = true
        }
    }


    def startNewRound = {
      bnGiveCard.enabled = false
      bnStand.enabled = false
      txtStake.enabled = true
      txtStake.text = ""
      bnNewRound.enabled = false
    }

    def updateCards() = {
      flowPanelN = new FlowPanel()

      val cards:List[Card] = round.getHandOfBank().getVisibleCards()

      for (card<-cards) {
          val cardComp = card.color + "_" + card.weight.toString
         // doesnt work ;D new card<->label match :) Name einer Variablen bekommen funz leider nicht...; flowPanelN.contents += cardComp.asInstanceOf[Label]
      }
    }

  }

} 