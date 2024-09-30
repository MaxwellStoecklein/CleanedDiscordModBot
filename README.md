# CleanedDiscordModBot

This demo program is a discord bot with a set of basic features. It includes: 
- A command (!parsenumbers) that returns a set of numbers inputted by the user, as well as their sum. 
- A command (!delete) to delete the last message sent, which would be the one that called the command.
- A command (!snipe) to retrieve the last 3 messages that were deleted in a given channel. This command can recall deleted messages from entirely different channels at the same time, meaning
a message deleted in a separate channel won't interfere with the retrieval of a deleted message in the targeted channel.
