import io
import flask
from flask import Flask, jsonify, request, send_file
from uploads import UploadsCollection
from detector import FoodDetector
from PIL import Image

saved_maxsize = 512

app = Flask(__name__)

@app.route('/')
def root():
    return 'Can I Eat It Server'

@app.route('/list')
def list():
    return jsonify([upload_img_json(i)
        for i in UploadsCollection.getinstance().images_list()])


# Test using:
# curl -F image=@<path> http://localhost:5000/upload
@app.route('/upload', methods=['POST'])
def upload():
    if 'image' not in request.files:
        flask.abort(400)
    img = Image.open(request.files['image']).convert('RGB')
    result = FoodDetector.getinstance().detect(img.copy())
    img.thumbnail((saved_maxsize, saved_maxsize))
    i = UploadsCollection.getinstance().add_image(img, result)
    return jsonify(upload_img_json(i))

@app.route('/image/<int:img_id>.png')
def get_image(img_id):
    upload = UploadsCollection.getinstance().get(img_id)
    if upload is None:
        flask.abort(404)
    return send_file(io.BytesIO(upload.get_png()),
            attachment_filename=('%d.png' % (img_id,)),
            mimetype='image/png')

def upload_img_json(upload_image):
    return {'id': upload_image.get_id(),
            'date': upload_image.get_upload_date(),
            'size': upload_image.get_filesize(),
            'result': upload_image.get_result()}

# Make sure the FoodDetector will be ready when needed
FoodDetector.getinstance()
