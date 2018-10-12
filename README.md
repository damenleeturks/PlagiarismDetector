# PlagiarismDetector
Basic command-line program that performs plagiarism detection using a N-tuple comparison algorithm allowing for synonyms in the text.

<h3>Sample Usage using Gradle wrapper</h3>

```
$ ./gradlew build run
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:jar UP-TO-DATE
:assemble UP-TO-DATE
:compileTestJava UP-TO-DATE
:processTestResources UP-TO-DATE
:testClasses UP-TO-DATE
:test UP-TO-DATE
:check UP-TO-DATE
:build UP-TO-DATE
:run
Running Test
Synonyms File: synonyms.txt
run sprint jog

Tuple File: file1.txt
go for a run

Tuple File: file2.txt
go for a jog

Num matches 2
% of tuples that match: 100.0
Synonyms File: synonyms.txt
run sprint jog

Tuple File: file1.txt
go for a run

Tuple File: file3.txt
went for a jog

Num matches 1
% of tuples that match: 50.0
SUCCESS: Tests passed

BUILD SUCCESSFUL

Total time: 2.798 secs
```

<h3>Sample Usage using Command Line</h3>

Run with no args to execute the internal tests
```
$ java -cp bin com.kellyfj.PlagiarismDetector
Running Test
Synonyms File: synonyms.txt
run sprint jog

Tuple File: file1.txt
go for a run

Tuple File: file2.txt
go for a jog

Num matches 2
% of tuples that match: 100.0
Synonyms File: synonyms.txt
run sprint jog

Tuple File: file1.txt
go for a run

Tuple File: file3.txt
went for a jog

Num matches 1
% of tuples that match: 50.0
SUCCESS: Tests passed
[
```

Run with arguments 
```
arg1 = file containing synonyms
arg2 = file containing strings to compare
arg3 = file containing our baseline strings to compare against
arg4 = [Optional] Size of each nTuple
```

Example
```
$ java -cp bin com.kellyfj.PlagiarismDetector synonyms.txt file1.txt file2.txt 
Synonyms File: synonyms.txt
run sprint jog

Tuple File: file1.txt
go for a run

Tuple File: file2.txt
go for a jog

Num matches 2
% of tuples that match: 100.0
```
or
```
$ java -cp bin com.kellyfj.PlagiarismDetector synonyms.txt file1.txt file2.txt 2
Synonyms File: synonyms.txt
run sprint jog

Tuple File: file1.txt
go for a run

Tuple File: file2.txt
go for a jog

Num matches 3
% of tuples that match: 100.0
```
