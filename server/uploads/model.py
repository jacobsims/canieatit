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
        return len(self.collection.db.execute('SELECT rowid FROM Imgs WHERE rowid = ?',
            [self.img_id]).fetchall()) > 0

    def get_upload_date(self):
        return self.collection.db.execute('SELECT datetime FROM Imgs WHERE rowid = ?',
                [self.img_id]).fetchone()[0]

    def get_result(self):
        return self.collection.db.execute('SELECT analysis FROM Imgs WHERE rowid = ?',
                [self.img_id]).fetchone()[0]

    def get_png(self):
        return self.collection.db.execute('SELECT data FROM Imgs WHERE rowid = ?',
                [self.img_id]).fetchone()[0]

    def get_filesize(self):
        return self.collection.db.execute('SELECT length(cast(data as blob)) FROM Imgs WHERE rowid = ?',
                [self.img_id]).fetchone()[0]

    def set_result(self, analysis):
        cur = self.collection.db.cursor()
        cur.execute("UPDATE Imgs SET analysis = ? WHERE rowid = ?",
                [analysis, self.img_id])
        self.collection.db.commit()

class UploadsCollection():

    """
    A class representing the whole collection of uploaded images.
    Interfaces with the database.
    """

    instance = None

    def __init__(self):
        self.db = database.initialize()

    def add_image(self, img, result):
        with io.BytesIO() as output:
            img.save(output, format='PNG')
            buf = buffer(output.getvalue())
        cur = self.db.cursor()
        cur.execute("INSERT INTO Imgs VALUES(strftime('%s', 'now'), ?, ?)",
                [buf, result])
        img_id = cur.lastrowid
        self.db.commit()
        return UploadImage(self, img_id)

    def images_list(self):
        return [UploadImage(self, x[0])
                for x in self.db.execute('SELECT rowid FROM Imgs ORDER BY datetime DESC')]

    def get(self, img_id):
        i = UploadImage(self, img_id)
        return i if i.exists() else None

    @classmethod
    def getinstance(c):
        if c.instance is None:
            c.instance = c()
        return c.instance

