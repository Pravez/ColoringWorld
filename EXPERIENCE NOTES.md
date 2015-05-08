# Experience notes for this project

## Sensors
With LibGDX, when you want to avoid collisions between two objects, you can use the the group index of the filter of 
the fixtureDef of the body of the element : body.fixtureDef.filter.groupIndex
Two bodies in two different groups won't collide. But you won't be able to check the contact between them by using
the ContactListener class, there are no contact between two bodies from two different groups.
So to prevent collision between them and check the contact between them meanwhile, the object has to be a sensor.
A sensor is a body who won't collide with other objects but will still create a contact event with them.
To make a body become a sensor, you have to do : body.fixtureDef.isSensor = true

## Collisions
It is the perfect example for the double jump bug or the infinity jump on walls. The fact is that a jump cannot be
re-called when a character is aloft. To avoid this problem, we must make difference between a vertical platform or a horizontal
platform. So for example compare x and y axis.