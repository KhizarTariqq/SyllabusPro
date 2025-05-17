import spacy
nlp = spacy.load("en_core_web_sm")

# Create doc object
doc = nlp(extracted_text)
#for ent in doc.ents:
    # Print the entity text and its label
    #print(ent.text, ent.label_)