# Repos Project
This is a application to allow user to browse through the latest gitHub repositories.

**Link to project:** https://github.com/noamaw/Repos

## How It's Made:

**Tech used:** Kotlin, Ktor, Koin, Coil, Room, Compose

Utilizing Ktor for the network request, passing the data to the Repository where we combine the Favorite list data from the local Room database.
When user clicks on the time frame they would like to browse the action is sent to the viewModel that requests from the Repository - combining the data from the network, and the local room data base. returns a list of GitRepositories. that is updated into the StateFlow. to allow for Composition of Compose UI.

**Reasoning:**
I think that the MVVM pattern is currently the best option for separation of concerns, I did make a small mistake with the not shared viewModel (not supported in Koin) which caused me to move some of the functionality to the Repository class.
I decided to use all the Kotlin based libraries to hypothetically allow for KMP implementation later on, to turn the project into a Cross Platform App.

## Optimizations
  1. Would change the StateFlow that is not emitting data right away, allowing the user to update the favorites by clicking on the Heart icon even in the main list.
  2. Would add a cache to avoid needing to redownload the avatar images everytime.
     
## Lessons Learned:

To not postpone the work on the Assignment to late. but Also understand that I have my life constraints, so be happy with what you were able to achieve.


