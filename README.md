Welcome to the JavaFX LineEditor!

A single window desktop application where you can draw lines and play around with them!

  !   Built using the foundations of Model-View-Controller architecture which allows separation of the components
  for scalability, reusability and convenience.
  !   Allows resizing of the line using an anchor point and allows transforms such as clockwise & anti-clockwise
  rotations around the centre point of an object.
  !   Allows Grouping and Ungrouping of lines as well as groups of lines. Groups can also be transformed using
  appropriate buttons. (LEFT, RIGHT, UP, DOWN)
  !   Special Feature: Allows Undo and Redo using the keys Z and R respectively. Using Backward Undo mechanism actions
  can be undone and those undone actions can also be redone. The applications also allows other operations such as
  dragging of object (line or group), deleting an object, snapping of the line when creating and/or dragging.

  Despite being a single screen application at the moment, the MVC architecture allows secured and independent storage
  of model's internal components and access through appropriate Public APIs. A subscriber pattern and a gatekeeper
  has been implemented to capture and notify model changes to appropriate components of the application. This degree
  of separation allows to scale the model to include multiple views (single or multiple screens), to have multiple
  models to accommodate multiple objects and to build controllers to capture events separately for each view if needed.

  I hope you like my implementation of MVC architecture. Please leave a like or email me on yashab128151@gmail.com to
  learn more about the MVC Architecture and how to implement it.

Thank You for Visiting!
