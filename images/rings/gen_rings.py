from PIL import Image
import random

def clip(number):
    return max(min(number, 255), 0)

width = 1024
height = 1

for i in range(50):
    img = Image.new(mode = "RGBA", size = (width, height))

    random_threshold = 5
    color_seed = random.randint(0, 255)
    alpha_seed = random.randint(0, 32)

    r = 0
    g = 0
    b = 0
    a = 0

    datas = img.getdata()

    newData = []
    for item in datas:
        r = clip(color_seed + random.randint(-random_threshold, random_threshold))
        g = clip(color_seed + random.randint(-random_threshold, random_threshold))
        b = clip(color_seed + random.randint(-random_threshold, random_threshold))
        a = clip(alpha_seed + random.randint(-random_threshold, random_threshold))
        
        newData.append((r, g, b, a))
        
        color_seed = clip(color_seed + random.randint(-random_threshold, random_threshold))
        alpha_seed = clip(alpha_seed + random.randint(-random_threshold, random_threshold))

    tosave = Image.new(mode = "RGBA", size = (width, 8))
    tosavedata = []
    for x in range(8):
        tosavedata.extend(newData)

    tosave.putdata(tosavedata)
    tosave.save("tex_ring_{0}.png".format(i + 50), "PNG")
