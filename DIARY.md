# May 29, 2015
* Continued work on DrawManager, changed way to save elements
* Problem with tiled and the managing of transparency - resolved ?
* Started to think about how to change way to load static tiles from tiled

# May 28, 2015
* Started to search musics, and found some
* Started to create the entire theme of the game
* Did some work about sprites and their integration in the game
* Started the work on a DrawManager (testing the item)

# May 27, 2015
* Started to think about what to polish, to refactor on the program
* Started to describe the scene of the game, the plot
* Did some modifications concerning tiled

# May 26, 2015
* Finished level design for this version
* Modified falling platforms so that they fall according to x and y axis (not only x)
* Modified a bit the UI
* corrected some bugs for enemies
* Prepared game for testers
* Released v0.3.3b containing levels and bugfixes
* Released v0.4 and sent it to testers

# May 25, 2015
* Finished implementing reader of .tmx files. Every element can now be interpreted, see tutorial.
* Bugfixes concerning enemies and falling platforms
* Bugfixes concerning pixmaps and graphic components
* Started to create some levels for testers

# May 24, 2015
* Nothing

# May 23, 2015
* Started to work on graphic components for elements.

# May 22, 2015
* Continuing and finishing work on ColoredMagnets
* Released v0.3.2
* Continuing work on .tmx files, now completely finished work on game MapObjects, from Tiled.
* Implemented interpretation of MovingPlatforms

# May 21, 2015
* Finished implementing static platforms into our conception from .tmx files
* Created some UI for user when a problem occurs on the loading of a level
* Started work on ColoredMagnets

# May 20, 2015
* Started implementation of parser for reading .tmx files from Tiled
* Concerning the parser, created a complicated way to create for example only 1 platform instead of 10
* Continued implementation of the parser. Now possibility to extract from .tmx files platforms and color platforms
* Implemented score system
* Added some funny features concerning score
* Released v0.3.1 and v0.3.1a with scores adjustments

# May 19, 2015
* Added level for testers
* Rectified bug with enemies
* Added enemies contact
* Modified enemies contacts
* Released v0.3 and send it to test

# May 18, 2015
* Some bugfixes and correctives
* Finished work on secondary colors
* Changed magnets to ColoredMagnets, and started to work on it
* Finished work on secondary colors with platforms and enemies
* Started work on a new graphic way to show color gauges
* Added and modified some levels for testers

# May 17, 2015
* Completed keymapper, needs some work by the way
* Created in the menu the level selector

# May 16, 2015
* Started work on updating management of colors
* Started work on implementation of secondary colors (for arts)
* Created new commands ... others according to the secondary colors. Modified current enemies
* Started work on keymapper

# May 15, 2015
* Possibility to move the view during pause
* Updated collisions with enemies
* Rectified some things about jumping enemies
* Got first results of testers : some levels are too difficult and controls are awkward.
* Started to modify the game according to results of testers, see files associated
* Added Bouncing platforms according to a color
* Added progressive jump

# May 14,  2015
* Got many testers
* Created levels for testers. They are saved, and are 8.
* Corrected collision bug with platforms
* Corrected squat bug
* Character can now stay on falling platforms without dying
* Fixed some other minor bugs
* Created metrics, to save player's data (number of deaths, time played ...)
* Generated a jar for version 2.4 and sent it with the form to testers.

# May 13, 2015
* Got for now 4-5 testers. Will try to get a little bit more
* Finished work on changing collisions. Merged the branch on which the work was done to 0.2-dev
    * Go and see Experience Notes for further information
* Added some levels to test
* Started to create form to let testers write their opinion

# May 12, 2015
* Sent messages to some people to ask them to test
* Prepared four stages to test
* Continued work on changing collision detection

# May 11, 2015
* Meeting and update of what to do
* Started to think about stages and testers
* Created a new Branch, 0.2.3-dev-collisions to work on collisions

# May 10, 2015
* bugfix of falling platforms
* falling platforms can kill dynamic bodies
* bugfix of moving platforms

# May 9, 2015
* Added the jumping enemies who can jump on platforms
* Changed a bit how physics access data concerning how the bodies can move
* Changed the way enemies change their directions
* Added moving platforms

# May 8, 2015
* Lost time in merges
* Added the squat command
* Added magnets to attract dynamic bodies on their center
* Added platforms that can be slippery or that can slow the character (or the dynamic body on it)
* Corrected bug of wall jump
* Added a collection of levels to test every element of gameplay
* Released version 0.2
* Version 0.2.1 with enemies who can die, move and prevent from falling

# May 7, 2015
* Version 0.1.5 with the WindBlower class which can push a dynamic element in a specific direction

# May 5, 2015
* Version 0.1.2 with the resume / pause to the game
* Version 0.1.3 with a button to resume or pause the game in the User Interface Stage
* Version 0.1.4 with the Teleporter class which can teleport a dynamic element to a specific position

# May 4, 2015 :
* Released version 0.1, starting dev on version 0.1.0
* /!\ waiting for confirmation for the release of the version 0.1
* Version 0.1.1 with the default state of Color Platforms (activated by default or not)

# May 3, 2015 :
* Started to compose music

# May 2, 2015 :
* Nothing

# May 1, 2015 :
* Updating physics, part 3 : corrections on the wall jump
* Correction concerning the use of states. Every state is now used
* Work began on the wall jump, but won't be used now. (Probably later, as a feature)
* Reworked classes concerning components of elements.
* Java doc

# April 30, 2015 :
* Bugfixes
* Updating physic for the jump
* Release 0.0.1 according to the roadmap, with debug tools.

# April 29, 2015 :
* Continuing work on refacto
* Some tests on how enhancing collisions
* Better comprehension of Box2D
* Tests on the tutorial
* Better physic (but must be really finished)

# April 28, 2015 :
* Continuing work on refacto
* Review of the class diagram, some changes about commands and states

# April 27, 2015 :
* Started work on the refacto
* Updated class diagram

# April 26, 2015 :
* Created a branch for development
* Some tests with the assets

# April 25, 2015 :
* LudumDare version of the project pushed in a new branch
* Release of the LudumDare version
* Update of the README
* New Empty LibGDX project pushed in a new branch


# April 24, 2015 :
* Gathered information about dyn4j
* Gathered information about JavaBox2D
* "Finished" class diagram

# April 23, 2015 :
* Gathered information about classes brought by LibGDX (assets, faders ...)
* Started class diagram
* Starting the DIARY, CHANGELOG
