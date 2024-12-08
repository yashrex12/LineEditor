Student Name: Yash Borvankar
Student Number: 11306451
NSID: yab145
Course Name: CMPT 381

381-T1-2024-assignment-4-v1

Welcome to the JavaFX LineEditor!

All the classes have been created and MVC architecture, as discussed in class, has been implemented.
    Internal elements and variable are private and have appropriate public APIs
    Subscriber interface maintains a publish-subscriber mechanism
    Groupable interface allow items to be grouped together
    DCommand interface allow undo and redo operations.

Part1: All features implemented
    Shift down on background creates a line
    Hovering near a line creates hover effect
    Dragging endpoint resizes line and snaps to the grid
    Moving can be achieved by dragging around a selected a line
    LEFT key rotates an object anti-clockwise and RIGHT key rotates clockwise
    DOWN key scales down an object and UP key scales up an object
Part 2: All features implemented
    Control down on an object selects/deselects an object
    Control down and dragging on background creates rubberband rectangle selection
    Objects can be grouped together using G key and ungrouped using U key
    An object or multiple selected objects rotate and scale with respect to their centre points
    Objects in the group rotate around group's centre point
Part3: All features implemented except redo operation of Create
    Z key will undo a command and R key will redo a command
    All commands are implemented as described using techniques discussed in class
    Observed Bugs:
        Redoing of a create command does not create the line as before instead creates one in opposite direction
        Redoing of Grouping and Ungrouping may require a click on the background before and re-selecting.
To run the application, simple run the main() method in EditorApp
Git repository can be found at https://git.cs.usask.ca/cmpt381-2024-a4/yab145.git

Thank You!