To run these tests:

1. Log into the bluenose unix server.  
     bluenose.cs.dal.ca 

   You will need to use an ssh client such as ssh (Mac)  or 
   Putty (Windows).

   Note: You will need your CS id and password to log in.

   If you do not know how to do this, there are plenty of tutorials on
   the web on how to ssh into a unix server.

2. Create a bluenose directory and an assn1 subdirectory on the unix server

      mkdir -p csci3136/assn3

3. Change directories into assn3

      cd csci3136/assn3

4. Copy (upload) the tests_3.zip file to csci3136/assn3 on the unix 
   server.

   Again, if you do not know how to do this, there are plenty of tutorials 
   on the web on how to copy files to a unix server.

5. On the unix server, unzip the zip file.

     unzip tests_3.zip

6a. Run the script file to run the tests.

     ./test.sh

6b. If a test fails, you have see the differences in output by running in
    debug mode.

     ./test.sh debug

Notes:
a.  If you are using a Mac, you have a unix system already.  You run 
    Terminal.app and do everything locally instead of using a remote
    unix server.

b.  You can run the tests inside your IDE, but you will need to copy
    and paste the input file into the console and then visually inspect
    the output.  This is workable for the short small tests, but is
    less workable for the larger ones. 
