package control;

import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.input.keyboard.Keyboard;
import org.tbot.util.Condition;
import org.tbot.wrappers.WidgetChild;

/**
 * Created by Dorkinator on 5/11/2015.
 */

public class StringChatInterface {
    public static final int PARENT = 309;
    public static final int OPTION = 4;

    public static boolean makeX(int amount){
        WidgetChild button;
        button = chatInterfaceId() == 1 ? Widgets.getWidget(PARENT, OPTION) : Widgets.getWidget(PARENT, OPTION);
        if(button.interact("make x")) {
            if(!Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return !interfaceOpen();
                }
            },1000)){
                return makeX(amount);
            }
            Keyboard.sendText(amount + "", true, 200, 300);
            Time.sleep(150,300);
            return true;
        }
        return false;
    }
    public static boolean interfaceOpen(){
        return chatInterfaceId() > 0;
    }
    public static int chatInterfaceId(){
        WidgetChild chatInterface = Widgets.getWidget(PARENT, OPTION);
        return (chatInterface != null && chatInterface.isVisible()) ? 1 : 0;
    }
}