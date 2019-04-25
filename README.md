# ChatNearby
A p2p online chat Android App, can find nearby users based on GPS localization information. Alow add nearby users to contacts.

Function list
-----------
Account management:
    ![image](https://github.com/donaldmyshen/ChatNearby/blob/master/image/register.png)
    ![image](https://github.com/donaldmyshen/ChatNearby/blob/master/image/login.png)
    
    1. Register with name,email and password, through Firebase-Auth
    2. Login with email and password, through Firebase-Auth
    3. Allow user choose a image profile, will stored in Firebase-Storage. If not choose, will use a defaut image profile.
    4. Ask for collect GPS information athourity, and initialize localization data when register, stored in Firebase-ReatimeDatabase

Message mangement:
    ![image](https://github.com/donaldmyshen/ChatNearby/blob/master/image/menu.png)
    1. Load the recent connected user list, and contains user' image profile, lastest message and its time. 
    2. Can send and recieve text message as a chat APP should be. Load both users' image profiles, load message with sending time.
    
    
Contacts mangement:
    ![image](https://github.com/donaldmyshen/ChatNearby/blob/master/image/contacts.png)
    ![image](https://github.com/donaldmyshen/ChatNearby/blob/master/image/loading.png)
    ![image](https://github.com/donaldmyshen/ChatNearby/blob/master/image/nearby.png)
    1. Use an algorithm to calculate the distance by given two points' latitude and longtitude.
    2. If distance is smaller than a certain threshold, the users will be load into a nearby list for check.
    3. Allow current user add other users from nearby list to cantacts.
    4. Allow current user choose other users in the contacts list to start a dialog.


Interesting feature detais
----------
    1. Load a welcome Gif when login. 
    2. Load a loading Gif before find users near by.
    3. Allow users refresh the lists by guesture (pull down).
    4. Change the animation to make more smooth.

Further plan
-----------
    1. Add public post.
    2. Add validation adding request.
    3. Add email validation when register.(Actually almost finished this part)
    
