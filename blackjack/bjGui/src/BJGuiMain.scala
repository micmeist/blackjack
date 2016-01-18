import scala.swing._
import scala.swing.BorderPanel.Position._
import javax.swing.ImageIcon
import event._


object BJGuiMain extends SimpleSwingApplication {
  
  def top = new MainFrame {
    
    title = "Blackjack"
    
    // alle Kartenbilder als Labelicons
    // atm nur Spade
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
    val bnGiveCard = new Button {
      text = "Hit me!"
    }
    val bnStand = new Button {
      text = "Stand"
    }
    val bnStart = new Button {
      text = "Start the game!"
    }
    val lblStake = new Label {
      text = "Stake in $: "
    }
    val txtStake = txtField
    val bnNewRound = new Button {
      text = "New Round!"
    }
    
    def txtField = new TextField {
      horizontalAlignment = Alignment.Left
    }
    
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
      icon = new ImageIcon("blackjack.png")
    }
    /*val lblCenterWon = new Label {
      icon = new ImageIcon("winner.jpg")
    }
    val lblCenterLost = new Label {
      icon = new ImageIcon("lost.jpg")
    }*/
    
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
      c.gridy = 1
      c.gridwidth = 2
      layout(bnStart) = c
      
      c.gridx = 0
      c.gridy = 2
      layout(bnNewRound) = c
    }
    
    //beim Start jeder neuen Runde
    startNewRound
    
    // diese dann später dynmaisch füllen
    val labelPic1 = new Label {
      icon = new ImageIcon("diamond_6.png")
    }
    val labelPic2 = new Label {
      icon = new ImageIcon("heart_3.png")
    }
    val flowPanelN = new FlowPanel()
    val flowPanelS = new FlowPanel(labelPic1, labelPic2) 
  
    contents = new BorderPanel {
      layout(gridBagPanelW) = West
      layout(lblCenterDefault) = Center
      layout(flowPanelN) = North
      layout(flowPanelS) = South
      layout(gridBagPanelE) = East
    }
    
    
    listenTo(bnGiveCard)
    listenTo(bnStand)
    listenTo(bnStart)
    listenTo(txtStake.keys)
    listenTo(bnNewRound)
    
    
    reactions += {
      case ButtonClicked(component) if component == bnGiveCard =>
        // fordere neue Karte von Kontroller an "newCard"
        // kovertiere Kartenobjekt in GuiKomponente->Bild
        // adde Gui Komponente zum Panel
        // flowPanelS.contents += newCard
      case ButtonClicked(component) if component == bnStand =>
        // call controller logic to see if user won
        // if (controller.userWon == true) 
        lblCenterDefault.icon = new ImageIcon("winner.jpg")
        // else lblPicCenter.icon = new ImageIcon("lost.jpg")
        bnGiveCard.enabled = false
        bnStand.enabled = false
        bnNewRound.enabled = true
      case ButtonClicked(component) if component == bnStart =>  
        bnStart.enabled = false
        bnGiveCard.enabled = true
        bnStand.enabled = true
      case ButtonClicked(component) if component == bnNewRound =>  
        startNewRound
      case KeyPressed(_, Key.Enter, _, _) =>
        //def isAllDigits(x: String) = x forall Character.isDigit
        if(!(txtStake.text.forall { _.isDigit })) {
          Dialog.showMessage(new FlowPanel, "Please use numbers only!")
          txtStake.text = ""
        } else {
        txtStake.enabled = false
        bnStart.enabled = true 
        }
    }
    
    
    def startNewRound = {
      bnGiveCard.enabled = false
      bnStand.enabled = false
      txtStake.enabled = true
      txtStake.text = ""
      bnStart.enabled = false
      bnNewRound.enabled = false
    }
    
  }  
  
} 