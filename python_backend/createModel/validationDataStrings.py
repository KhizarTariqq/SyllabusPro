# Read txt files into strings

with open("txts/STA302F2024_syllabus.txt", "r", encoding="utf-8") as f:
    STA302 = f.read()

with open("txts/Syllabus_STA304_Fall2024.txt", "r", encoding="utf-8") as f:
    STA304 = f.read()