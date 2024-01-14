# lineSearchServer-EH

Author - Eian Hiss
Course - CEN 3024C
CRN - 24668

A server and loopback client combination that searches a document by line number and displays the found line and the four lines surrounding it.
Created as the final project for COP 2805C.

LineServer - Searches provided document for a line by number provided by LineClient, then provides five lines centered on the specified line to LineClient.
LineClient - GUI interface, connects to LineServer and sends it a line number, and receives lines found by LineServer to display in the GUI.
