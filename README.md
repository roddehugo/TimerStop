# Stop o processus after an elapsed time

You're allowed to use this source as you want, it's free an open source !

To run, juste cheked if you have a JRE installed, such as Sun JRE by Oracle or an open one like OpenJDK, and then run it as a normal application, by double clicking on the file.

!! Up !!
In the working directory, with command line executable please use arguments like this :
   $ java -jar "TimerStopCmd.jar" [processustokill] [delay] [exit:0|1]

for instance to kill firefox after 10 minutes and then shutdown the computer :
   $ java -jar "TimerStopCmd.jar" opera 10 1

without shutdowning the computer :
   $ java -jar "TimerStopCmd.jar" opera 10 0

or with the .exe file :
   $ TimerStopCmd.exe opera 10 1
