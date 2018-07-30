wumpus
======

A simple test designed for the interview process at Connected Health Services. 

Please fork this repo in your account and develop a Java program that implements the rules for the Wumpus Game (see included Powerpoint). You are expected to write the complete game logic, and use simple text interactions to drive the game play (like the text-based adventure games from the old days, http://en.wikipedia.org/wiki/Text-based_game).

Bonus points for unit tests ;).

Please use Eclipse as your IDE and whatever version of Java you are most confortable with (Scala or Groovy also welcomed). Use whatever framework you prefer for your unit tests.


Requirements
=============

Runtime
-------

- Java JDK 8
- Maven 3.3 or higher

Develop
-----------

- Runtime requirements
- And IDE (as Eclipse) or Text editor 


Architecture
==============

The game has been create as a extensible dungeon game: developer can implement or extends predefined asset to improve the game:

- Can create an new UI
- Can create its own Dungeon with more dangers and items
- Can extend the player to add new action

All the assets uses _Interfaces_ which defines the contract with the other assets.

TODO: Asset registration should be move to use Java ServiceLocator utility
 

Run the game
============

Run with maven
---------------

Just execute:

    $ mvn clean install exec:java
  
  
To show debug messages use

    $ mvn -P debug clean install exec:java

or

    $ mvn -P trace clean install exec:java
    
    
Run with Eclise
----------------

- Import the project as _Existing Maven Project_ into workspace.
- Select `Game` class a launch (or debug) it as _Java Applicaton_.

Configuration
--------------

Game will ask to user about all configuration available to play the game. Every option will
be validated (if any problem found the game will ask for it again).


