from database import *
from PIL import Image
import io
import database

class UploadImage():

    """
    A class representing a single uploaded image.
    Interfaces with the database.
    """

    def __init__(self, collection, img_id):
        self.collection = collection
        self.img_id = img_id

    def get_id(self):
        return self.img_id

    def exists(self):
        with database.connect() as conn:
            return len(conn.execute('SELECT rowid FROM Imgs WHERE rowid = ?',
                [self.img_id]).fetchall()) > 0

    def get_upload_date(self):
        with database.connect() as conn:
            return conn.execute('SELECT datetime FROM Imgs WHERE rowid = ?',
                    [self.img_id]).fetchone()[0]

    def get_result(self):
        with database.connect() as conn:
            return conn.execute('SELECT analysis FROM Imgs WHERE rowid = ?',
                    [self.img_id]).fetchone()[0]

    def get_png(self):
        with database.connect() as conn:
            return conn.execute('SELECT data FROM Imgs WHERE rowid = ?',
                    [self.img_id]).fetchone()[0]

    def get_filesize(self):
        with database.connect() as conn:
            return conn.execute('SELECT length(cast(data as blob)) FROM Imgs WHERE rowid = ?',
                    [self.img_id]).fetchone()[0]

class UploadsCollection():

    """
    A class representing the whole collection of uploaded images.
    Interfaces with the database.
    """

    instance = None

    def __init__(self):
        database.initialize()

    def add_image(self, img, result):
        with database.connect() as conn:
            with io.BytesIO() as output:
                img.save(output, format='PNG')
                buf = buffer(output.getvalue())
            cur = conn.cursor()
            cur.execute("INSERT INTO Imgs VALUES(strftime('%s', 'now'), ?, ?)",
                    [buf, result])
            img_id = cur.lastrowid
            conn.commit()
            return UploadImage(self, img_id)

    def images_list(self):
        with database.connect() as conn:
            return [UploadImage(self, x[0])
                    for x in conn.execute('SELECT rowid FROM Imgs ORDER BY datetime DESC')]

    def get(self, img_id):
        i = UploadImage(self, img_id)
        return i if i.exists() else None

    @classmethod
    def getinstance(c):
        if c.instance is None:
            c.instance = c()
        return c.instance

