import spacy
nlp = spacy.load("output/model-best")

# Create doc object
with open("testing_txts/20229_CSC398H5F_LEC0101.txt", "r", encoding="utf-8") as f:
    CSC420 = f.read()

doc = nlp(CSC420)

for ent in doc.ents:
    # Print the entity text and its label
    print(ent.text, ent.label_)