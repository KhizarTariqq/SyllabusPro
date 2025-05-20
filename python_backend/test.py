def read_char_range(file_path, start, end):
    """
    Reads characters from `start` to `end` (exclusive) in the file at `file_path`.
    """
    with open(file_path, 'r', encoding='utf-8') as f:
        text = f.read()
    return text[start:end]

#[3572,3616,"SYLLABUSITEM"],[3617,3676,"SYLLABUSITEM"],[3677,3737,"SYLLABUSITEM"],[3738,3756,"SYLLABUSITEM"],[3757,3804,"SYLLABUSITEM"]
file_path = 'txts/20239_CSC324H5F_LEC0101.txt'
start = 3572
end = 3616

chunk = read_char_range(file_path, start, end)
print(f"Characters {start}-{end}:\n{chunk}")
