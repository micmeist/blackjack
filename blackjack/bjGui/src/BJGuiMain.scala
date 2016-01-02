
import scala.swing._
import scala.swing.BorderPanel.Position._
import javax.swing.ImageIcon
import event._


object BJGuiMain extends SimpleSwingApplication {
  
  def top = new MainFrame {
    
    title = "Blackjack"
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
      text = "Stake: "
    }
    val txtStakes = txtField
    
    def txtField = new TextField {
      text = ""
      horizontalAlignment = Alignment.Left
    }
    
    val gridPanelW = new GridPanel(2,1) {
      contents += bnGiveCard
      contents += bnStand
    }
    
    val labelPicCenter = new Label {
      icon = new ImageIcon("blackjack.png")
    }
    
    val gridPanelE = new GridPanel(2,1) {
      contents += lblStake
      contents += txtStakes
      contents += bnStart
    }
    
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
      layout(gridPanelW) = West
      layout(labelPicCenter) = Center
      layout(flowPanelN) = North
      layout(flowPanelS) = South
      layout(gridPanelE) = East
    }
    
    
    listenTo(bnGiveCard)
    listenTo(bnStand)
    listenTo(bnStart)
    listenTo(txtStakes.keys)
    
    
    reactions += {
      case ButtonClicked(component) if component == bnGiveCard =>
        // fordere neue Karte von Kontroller an "newCard"
        // kovertiere Kartenobjekt in GuiKomponente
        // adde Gui Komponente zum Panel
        //flowPanelS.contents += newCard
      case ButtonClicked(component) if component == bnStand =>
      case ButtonClicked(component) if component == bnStart =>  
      case KeyPressed(_, Key.Enter, _, _) =>   
        lblStake.text += txtStakes.text
    }
  }  
  
} 
  
