# Roadmap of the game

## Introduction

In this document, is presented all the different general tasks around the game, and its features in developement or scheduled. All the tasks are gather around different versions of the game.
With this, you will be able to have an overview of the game and its future.

## Version 0.0.A - Prototype
* a player
* the player can move and jump (physics)
* world with static and dynamic objects
* color blocks
* the player can “activate” color blocks
* color blocks are transparent and without consistance if not activated
* 1 level
* the player can die by falling

## Version 0.0.B - Ludum Dare
* the camera follows the player
* exit
* 3 more levels
* spikes
* animated sprites
* basic tutorial
* menu
* options (change the volume)

## Version 0.1 - Rebase of the Game
* establish the roadmap
* create a class diagram
* create a new repository
* save the first prototype as version 0.0A
* save the Ludum Dare game as version 0.0B
* reworking/refacto of the code
* start a changelog file
* write the basic story of the game
* adding cheatcodes : change current level, restart level, teleport to mouse position, … (debug tools)

## Version 0.2 - Adding features
* the game can be paused and resumed
* the character can squat/duck
* the color platforms can have a default state such as activated and by activating the color, they become disabled
* wind blowers
* adding magnetic fields to the character that he can activate next to magnet and hang to the magnet
* adding magnetic fields on the ground or anywhere that attracts the character
* teleporters in the level
* slippery zones
* zones that grip the character to the floor 

## Version 0.3 - Enemies and dynamic things, secondary colors
* Enemies (basic ones)
* moving and falling platforms
* enemies are sensible to colors
* secondary colors (purple, green and orange)
* secondary color platforms
* Possibility to see the entire level while the game is paused
* Possibility to change controls
* Possibility to select level

## Version 0.4 - User interface & level loader
* score system (time, number of attempts, …)
    - (a map to select the level to play)
    - save the score
* Fun in the menu
    - sounds in the menu
    - buttons effects (hover, active, …)
* load a level from a file

## Version 0.5 - Let's add some graphics
* progressive tutorial with notices displaying sentences in the back spread in the level
* sprites (character, blocks)
* animations
* animation of death : the game runs slower
* add music


## Version 0.6 - Bonuses
* definition of more complex level exit (code to enter, keys to find, color to activate, …)
* (10 more levels)
* hidden exits to secret levels that have to be found in order to play it
* full screen mode of the game
* the game can be resized
* hidden special items to find in each level (such as 3 stars) and which gives more points
* choice of the character to play with different characteristics for each of them (speed up, jump higher, reverse the gravity…)
* make the camera moving when landing or dying, when the boss walks or whatever
* adding a lot of sounds and effects
* add levers that can change segments of walls in order to access the exit or something else
* adding new maps of levels by dividing into different places
* changing the language of the game
* a boss at the end of a level such as an enemy who force the character to go in a certain direction and the time is limited
* a script for each enemy, such as a pattern

## Version 0.7 - Super Bonuses
* zones in the level which have special effect on the character when a color is activated or something else,
by default (when blue is activated, the gravity is reversed, …)