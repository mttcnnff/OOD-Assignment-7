# OOD-Assignment-7
Assignment 7: The Music Editor: Third Movement

This is the third part of the Music Editor project. This introduces the view and a simple 
controller to our model, as well as file read-in functionality. Given a properly formatted song 
document a model will be able to automatically read-in the specified contents into a full song. 
The implementation details are specified below.

## The Model

#### Interface
The model offers two interfaces, The IPlayerModel and the IPlayerModelReadOnly. The IPlayerModel 
offers all functionality described below, whereas the IPlayerModelReadOnly offers strictly the 
read-only operations of the model. This acts as an adapter to the Views so the can never edit the
 model they are referenced to.

#### Implementation
The model is implemented in two different spots. There is a basic  model which allows all normal 
functionality including adding a note, removing a note, editing a note, song concatenation, and song
 combination. In addition, a model has "getters" used to receive read-only information about a 
 model. The read-only information available includes the song's length, the song's tempo, a list 
 of notes which start at a particular beat, a list of notes playing at a particular beat, the 
 full range of notes present in the piece from lowest to highest, and the full song represented 
 in a HashMap<Integer, List<INote>>. <i>(It is discouraged to use the getSong method to get the 
 entire song at once. Getting the whole song at once is inefficient in most uses however it is 
 included in this case should a user need it)</i>.
 
 ## The View
 
 #### Interface
 The Views in this project have one available interface, IView. The base interface, IView, offers 
 two main functions. One to make the view visible, usually called immediately after construction, 
 and another to refresh a view. 
 
 #### Implementation
 ##### Visual View
 A GUI display of the current song in this model. The bottom half of this GUI is a piano which 
 illuminates the currently playing notes yellow for the beat the view is currently at. The top 
 half is a "Note Map", which renders the notes for a song that a visible by the view at the 
 moment. Only rendering the visible beats means that the view only needs to request information 
 about those beats and **NOT** the whole song. This increases performance dramatically. In 
 addition, the Visual View is currently the only view which reacts to key presses. When a user 
 presses the left or right arrows the view will move back or forward one beat, respectively.

##### Textual View
 A console output of the music showing the notes played in the piece from lowest to highest in the
 left to right direction at the top. Each beat is denoted at the beginning of each row and is 
 padded to be right justified. Finally, the actual music is noted so that "X" indicates the head 
 of a note and a "|" indicates a sustained note.
 
##### Sequencer View
 A MIDI output of the song. There is no visual portion of this view, when it is made visible it 
 will automatically play the song through the computer's midi system. The program will close 
 immediately after playing.

##### Composite View
 A hybrid view of a visual view and a sequencer view. Allows a user to simultaneously see and 
 hear the song. Uses a fully synced controller.


## The Controller

#### Interface
 The Controllers in this project have one available interface, IController. IController offers two
 main functions. One to set the view, usually called immediately after construction and another 
 to start the operation of a controller.

#### Implementation
##### Basic Controller
 An extremely basic controller with essentially no functionality. All it is able to do is start a 
 view and then the program ends. Used for static views such as Textual View or Sequencer View.

##### Visual Controller
 A controller to be used with visual views to control tracking of song view. Allows user to move 
 beat up and down in view only. Intended for use with Visual View.

 *Controls*
 1. Left Key: Increments beat down by one.
 2. Right Key: Increments beat up by one.

##### Synced Controller
 A fully operational controller which provides user functionality to move beat in GUI up and down,
 jump to the beginning or end of a song, pause or play song, and add notes to the song by clicking
 the corresponding key on the piano. Intended for use with the Composite View.

 *Controls*
 1. Left Key: Increments beat down by one.
 2. Right Key: Increments beat up by one.
 3. Home Key: Jumps current beat to the beginning of the song.
 4. End Key: Jumps current beat to the end of the song.
 5. Space Bar: Toggle play/pause of song.
 6. Left Click: Adds note you clicked on piano to song at current beat and advances song by one 
 beat.

##### Event Listeners
 The controllers use several event listeners to handle control input and sequencing. A keyboard 
 listener is used to listen for keyboard inputs on visual views, a mouse listener is used to 
 listen for mouse inputs on visual views, and a meta event listener is used to listen for meta 
 events from a sequencer.
 
 # Design Changes
 In this project I changed a few things. First I changed the implementation of the MIDI View to 
 use a sequencer instead of a receiver and renamed the class to Sequencer View. In addition I 
 made several controllers instead of just one to have different functionality for different views.
 
