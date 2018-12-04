"""
modified from find_food.py in github.com/wsu-wacs/seefood

Expects saved_model directory within current working directory.
"""
import argparse
import numpy as np
import tensorflow as tf
import uploads
import math
import scipy.stats
from PIL import Image
from threading import Lock

class FoodDetector():
    instance = None
    
    """Singleton class that represents the food detector"""

    # Returns a float from 0 to 1. Value less than 0.5 = not food.
    def score_convert(self, scores):
        difference = float(scores[0][0]) - float(scores[0][1])
        result = 1.0 / (1 + math.exp(-difference))
        return result


    def __init__(self):
        self.lock = Lock()
        with self.lock:
            self.sess = tf.Session()
            self.saver = tf.train.import_meta_graph('saved_model/model_epoch5.ckpt.meta')
            self.saver.restore(self.sess, tf.train.latest_checkpoint('saved_model/'))
            self.graph = tf.get_default_graph()
            self.x_input = self.graph.get_tensor_by_name('Input_xn/Placeholder:0')
            self.keep_prob = self.graph.get_tensor_by_name('Placeholder:0')
            self.class_scores = self.graph.get_tensor_by_name("fc8/fc8:0")

    def detect(self, img):
        with self.lock:
            resized = img.resize((227, 227), Image.BILINEAR)
            img_tensor = [np.asarray(resized, dtype=np.float32)]
            scores = self.sess.run(self.class_scores,
                    {self.x_input: img_tensor, self.keep_prob: 1.})
        return self.score_convert(scores)

    @classmethod
    def getinstance(c):
        if c.instance is None:
            c.instance = c()
        return c.instance
