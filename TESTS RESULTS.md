# RESULTS AND COMMENTS ON THE GAME MADE BY TESTERS


## Tests results for version v0.3

* There are some random bugs, for example a color that doesn't activate ...
* Bug with the death screen when being killed by a enemy
* Not enough levels to test
* Waiting for more stuff

### What we did :

    - Corrected the bug with the death screen which was called twice


## Tests results for version v0.2.4

* Last levels are way too difficult (levels 7 - 8)
* Character is just too slippery
* Jumping while being on a falling platforms reduces the jump force. Real but annoying.
* Changes have to be made on the using of colors : colors must have a greater impact on the game
* Changes must be done on controls
* UI needs to be updated (possibility to go to the menu while in game, options while playing ...)
* Idea of seeing the entire map before beginning the level ?
* Importance of some features, such as Magnet ? Needs to be more precise. Every feature must be useful and used.
* What about the "Die and Retry" ?

### What we did :

    - Adjusted character friction factor (finally linear dumping) with other elements
        * Is it really important ?
    - Removed useless platforms, useless features.
    - Added possibility when the game is paused to see the entire level.
    - Updated and added platforms
    - Added faculty for the character to jump higher with a longer press on spacebar
        * This removed the problem of a real jumping on falling platforms
    - Changed controls to "ZQSD" to move and "UIO" for colors
    - Added a level selector
    - Added possibility to change keymap
    - Complicated a bit the game with secondary colors
    - Added help concerning secondary colors, with a "chromatic circle"