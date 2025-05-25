import tabula

import camelot

pdf_path = 'pdfs/20249_MAT334H5F_LEC0101.pdf'
output_path = 'tabula_txts/' + pdf_path[5:-4] + '.txt'

# Load tables
tables = camelot.read_pdf(pdf_path, pages="all", strip_text='\n')

# Write each table to a text file
for i, table in enumerate(tables):
    # Clean newlines from each cell
    df = table.df
    # Convert to plain text
    text_output = df.to_string(index=False, header=True)

    with open(output_path, "w", encoding="utf-8") as f:
        f.write(text_output)





"""tables = tabula.read_pdf(pdf_path, pages='all', multiple_tables=True, output_format="dataframe")

with open(output_path, 'w') as f:
    for table in tables:
        f.write(table.to_string())
        f.write('\n\n') # Add a separator between tables
print(f"Table data saved to '{output_path}' successfully.")
"""