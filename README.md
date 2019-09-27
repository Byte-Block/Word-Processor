# Word-Processor
Final Project
Create a program that processes book's and pulls out different metric types. The programme must be realised 
as a class system in which the basic classes are in more detail described as follows.

Class Dictionary must contain information based on the table Dictionary from the SQLite database. The table
is created as such that besides the name, contains also more detailed information about the word definition.

Class Book models a book which the programme uses in the process. The contents of each book are found in
a unique text file.

Out of the necessary statistical handling, do the following:
-The possibility to find and store all the words that exist in the book, and are absent from the dictionary. Expand
the database with a new table which will be used for storing new words which are not found in the initial table with the purpose 
of potentially making better the existing dictionary.
-For each word that is contained in the dictionary, find out how many times it appears in the book.
-Find the first 20 most used words.
-Write in a text file all the words which are initially found and new words discovered, in such manner that they are lexicographically organized.
