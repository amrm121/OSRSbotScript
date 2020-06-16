# topBotScripts
cProFletcher - A script based in tasks and priority nodes (using round robin) to define the user's next action. 

# What was the differential
  - You could start from skill 0 using an ready-to-go button which just needed the resources in the bank. And you would stop at level **85 in fletching**. And add a task to make *n* MLongBow's aftewards.
  - If you just wanted to make a item using fletching skill to sell on the GE, you could just add the nodes of making the desired item from a dropDown list. 
  > _**It caused a big drop in prices while the script was functional. It had almost 20 players simutaneously using the script 24/7 and a ban rate of almost 5-10% because of the Node System implemented.**_
  - It had a round robin algorithm running through action nodes _(use bank, craft...)_, and each node had a priority value.
  This priority value (_a property of the [node class](https://github.com/amrm121/OSRStopBot/blob/master/cProFletcher/src/control/Node.java) which contains the action to do_) was made to always have the most important action _(ie.: check bag for resources to keep fletching)_ running.
    - The [action node](https://github.com/amrm121/OSRStopBot/tree/master/cProFletcher/src/scriptNodes) always beeing checked by the [Node Controller](https://github.com/amrm121/OSRStopBot/blob/master/cProFletcher/src/control/Controller.java) is the action that **must be running**. 
    - Human behavior was added by some random clicks, logouts, moves and it worked for a while before university became the priority and I couldn't continue in this project.

# TopBot is DEPRECATED
It's library derived from reverse engineering with ASM and the original client to retrieve bytecodes for each action and status that the client game sent to server.
Will try to retrieve it by my old computer and my RasPi which had a DB containing user's spent time and xp gained while using my script. Then it generates an image containing, the user spent time, xp gained, gold made (by market price and bows made), bows made and it's forum user name. Also it could generate the "Master Image" which I used to marketing, that contained the data of ALL users, all time spent, bows made and etc...
