# Code from here: https://docs.pyvista.org/examples/02-plot/texture.html

import pyvista as pv
import math
import numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import glob
import os

pv.global_theme.transparent_background = True
pv.global_theme.window_size = [800, 800]

sphere = pv.Sphere(
    radius=1, theta_resolution=120, phi_resolution=120, start_theta=270.001, end_theta=270
)

# Initialize the texture coordinates array
sphere.active_t_coords = np.zeros((sphere.points.shape[0], 2))

# Populate by manually calculating
for i in range(sphere.points.shape[0]):
    sphere.active_t_coords[i] = [
        0.5 + np.arctan2(-sphere.points[i, 0], sphere.points[i, 1]) / (2 * np.pi),
        0.5 + np.arcsin(sphere.points[i, 2]) / np.pi,
    ]

original_textures = [i for i in glob.glob("original_textures\*.png")]

result_folder = "results"
crop_folder = "results_crop"

if result_folder not in os.listdir():
    os.mkdir(result_folder)
    
if crop_folder not in os.listdir():
    os.mkdir(crop_folder)

for t in original_textures:
    print("Process " + t)
    # And let's display it with a world map
    tex = pv.read_texture(t)
    
    orig_filename = t[22:]
    
    result_file = result_folder + "\\" + orig_filename
    crop_file = crop_folder + "\\img_" + orig_filename[:-4] + "_crop.png"
    
    print("    Draw")
    plotter = pv.Plotter(off_screen=True)
    plotter.add_mesh(sphere, texture=tex)
    plotter.show(screenshot=result_file)
    
    print("    Crop")
    area = (174, 174, 626, 626)
    img = Image.open(result_file)
    img = img.crop(area)
    
    print("    Save: " + crop_file)
    img.save(crop_file)
