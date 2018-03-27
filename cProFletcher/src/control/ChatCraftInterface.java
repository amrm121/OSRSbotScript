package control;

import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.input.keyboard.Keyboard;
import org.tbot.util.Condition;
import org.tbot.wrappers.WidgetChild;

/**
 * Created by Dorkinator on 5/11/2015.
 */

public class ChatCraftInterface {
    public static final int PARENT = 305;
    public static final int PARENT_1 = 304;
    public static final int PARENT_M = 303;
    public static final int OPTION_S = 4;
    public static final int OPTION_L = 8;
    public static final int OPTION1_1 = 5;
    public static final int OPTION2_1 = 9;
    public static final int OPTION3_1 = 13;
    public static final int OPTION1 = 6;
    public static final int OPTION2 = 10;
    public static final int OPTION3 = 4;
    public static final int OPTION4 = 18;

    public static boolean makeX(int option, int amount){
        WidgetChild button;
        switch(option){
            case 1:
                button = chatInterfaceId() == 1 ? Widgets.getWidget(PARENT, OPTION1) : chatInterfaceId() == 2 ? Widgets.getWidget(PARENT_1, OPTION1_1) :Widgets.getWidget(PARENT_M, OPTION_S) ;
                break;
            case 2:
                button = chatInterfaceId() == 1 ? Widgets.getWidget(PARENT, OPTION2) : chatInterfaceId() == 2 ? Widgets.getWidget(PARENT_1, OPTION2_1) : Widgets.getWidget(PARENT_M, OPTION_L);
                break;
            case 3:
                button = chatInterfaceId() == 1 ? Widgets.getWidget(PARENT, OPTION3) : Widgets.getWidget(PARENT_1, OPTION3_1);
                break;
            case 4:
                button = Widgets.getWidget(PARENT, OPTION4);
                break;      	
            default:
                button = chatInterfaceId() == 1 ? Widgets.getWidget(PARENT, OPTION1) : Widgets.getWidget(PARENT_1, OPTION1_1);

        }
        if(button.interact("make x")) {
            if(!Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return !interfaceOpen();
                }
            },1000)){
                return makeX(option, amount);
            }
            Keyboard.sendText(amount + "", true, 250, 300);
            Time.sleep(150,300);
            return true;
        }
        return false;
    }
    public static boolean interfaceOpen(){
        return chatInterfaceId() > 0;
    }
    public static int chatInterfaceId(){
        WidgetChild chatInterface = Widgets.getWidget(PARENT, OPTION1);
        WidgetChild chatInterface1 = Widgets.getWidget(PARENT_1, OPTION1_1);
        WidgetChild chatInterfaceM = Widgets.getWidget(PARENT_M, OPTION_L);
        return (chatInterface != null && chatInterface.isVisible()) ? 1 : (chatInterface1 != null && chatInterface1.isVisible()) ? 2 : (chatInterfaceM != null && chatInterfaceM.isVisible()) ? 3 : 0;
    }
}