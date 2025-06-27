import os


folder = "C:\\Users\\55533250\\IdeaProjects\\MortalKombatProject\\src\\main\\resources\\images"

files = os.listdir(folder)

pngnames = {os.path.splitext(f)[0] for f in files if f.lower().endswith(".png")}


for file in files:
    if file.lower().endswith(".webp"):
        base_name = os.path.splitext(file)[0]
        if base_name in pngnames:
            fullpath = os.path.join(folder, file)
            os.remove(fullpath)
            print(f"Eliminado: {fullpath}")
