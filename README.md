# chutes-and-ladders

## Game Introductions:
![1685111545051](https://github.com/Zinaaan/chutes-and-ladders/assets/39329676/52acb385-4fe9-411d-8f55-569f6ccd7da3)

## Function requirements:
1. Specific size of the game board (n * n)
2. Each game has a different number of chutes and ladders
3. Allow multiple players playing, and each of them could know the current game process
4. Rules for player movement
5. System should have reasonable rules, for example, the chute shouldn't overlap with the ladder
6. Allow adding a new player or delete an existing player during the game playing

## Design patterns used:
1. **Policy pattern and Factory pattern**, injecting the Bean of game rules, rolling rules, turning rules, etc. along with the process of spring initializing by integrating with spring
2. **Builder pattern**, using builder pattern to ensure that complicated process of game building become more concise and specific.
3. **Observer pattern (pub-sub model)**, allow each participant noticing the game process and other players action. Using Spring ApplicationEventPublisherAware as a register, publish events along with the process of spring initializing as well as register as the lister by `@GameListener` annotation and detecting by SpringAOP.

## Tests:
Comprehensively **unit testing** and **integration testing**.
