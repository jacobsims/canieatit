"""
Uses a database file 'canieatit.db' in the current working directory.
"""

import sqlite3
import sys
import io

def create_schema(db):
    cur = db.cursor()
    cur.execute('''CREATE TABLE IF NOT EXISTS Imgs(
            datetime INTEGER,
            data     BLOB,
            analysis REAL)''')
    db.commit()

def initialize():
    db = None
    try:
        db = sqlite3.connect('canieatit.db')

        # First time, we should be making the table.
        create_schema(db)

    except sqlite3.Error as e:
        print('*************\nSQLITE ERROR: {}\n*************'.format(e))

    finally:
        db.commit()

    return db
