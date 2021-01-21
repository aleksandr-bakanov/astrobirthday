import sys
import os
import re
import sqlite3
from enum import Enum

regex = re.compile(r'(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?),(.*?)')

dbfile = "planets.db"

def toInt(value):
    try:
        return int(value)
    except ValueError:
        return None

def toFloat(value):
    try:
        return float(value)
    except ValueError:
        return None

class DiscoveryMethod(Enum):
    ASTROMETRY = 0
    DISK_KINEMATICS = 1
    ECLIPSE_TIMING_VARIATIONS = 2
    IMAGING = 3
    MICROLENSING = 4
    ORBITAL_BRIGHTNESS_MODULATION = 5
    PULSAR_TIMING = 6
    PULSATION_TIMING_VARIATIONS = 7
    RADIAL_VELOCITY = 8
    TRANSIT = 9
    TRANSIT_TIMING_VARIATIONS = 10

def discMethod(value):
    if value == 'Astrometry':
        return DiscoveryMethod.ASTROMETRY.value
    elif value == 'Disk Kinematics':
        return DiscoveryMethod.DISK_KINEMATICS.value
    elif value == 'Eclipse Timing Variations':
        return DiscoveryMethod.ECLIPSE_TIMING_VARIATIONS.value
    elif value == 'Imaging':
        return DiscoveryMethod.IMAGING.value
    elif value == 'Microlensing':
        return DiscoveryMethod.MICROLENSING.value
    elif value == 'Orbital Brightness Modulation':
        return DiscoveryMethod.ORBITAL_BRIGHTNESS_MODULATION.value
    elif value == 'Pulsar Timing':
        return DiscoveryMethod.PULSAR_TIMING.value
    elif value == 'Pulsation Timing Variations':
        return DiscoveryMethod.PULSATION_TIMING_VARIATIONS.value
    elif value == 'Radial Velocity':
        return DiscoveryMethod.RADIAL_VELOCITY.value
    elif value == 'Transit':
        return DiscoveryMethod.TRANSIT.value
    elif value == 'Transit Timing Variations':
        return DiscoveryMethod.TRANSIT_TIMING_VARIATIONS.value
    return None

def main():
    filepath = sys.argv[1]

    if not os.path.isfile(filepath):
        print("File path {} does not exist. Exiting...".format(filepath))
        sys.exit()
    if os.path.exists(dbfile):
        os.remove(dbfile)
    
    conn = sqlite3.connect(dbfile)
    cursor = conn.cursor()
    cursor.execute("""CREATE TABLE planets (
        pl_name TEXT PRIMARY KEY NOT NULL,
        id INT NOT NULL,
        
        hostname TEXT,
        sy_snum INT,
        sy_pnum INT,
        
        discoverymethod INT,
        disc_year INT,
        disc_facility TEXT,
        pl_refname TEXT,
        
        pl_orbper REAL,
        pl_orbsmax REAL,
        pl_rade REAL,
        pl_bmasse REAL,
        pl_orbeccen REAL,
        
        pl_eqt REAL,
        st_refname TEXT,
        st_spectype TEXT,
        st_teff REAL,
        st_rad REAL,
        st_mass REAL,
        sy_refname TEXT,
        sy_dist REAL,
        rowupdate TEXT,
        pl_pubdate TEXT,
        releasedate TEXT)
    """)
    
    # 365.256363004
    
    solar_planets = [('Mercury', 1, 'Sun', 1, 14, None, None, None, None, 87.969, 0.38709927, 0.3829, 0.055274, 0.20563593, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Venus', 2, 'Sun', 1, 14, None, None, None, None, 224.701, 0.723332, 0.9499, 0.815, 0.0068, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Earth', 3, 'Sun', 1, 14, None, None, None, None, 365.25, 1.00000261, 1.0, 1.0, 0.01671123, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Mars', 4, 'Sun', 1, 14, None, None, None, None, 779.94, 1.523662, 0.532, 0.107, 0.0933941, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Ceres', 5, 'Sun', 1, 14, None, 1801, None, None, 1680.5, 2.7653, 0.072914, 0.000157268, 0.07934, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Jupiter', 6, 'Sun', 1, 14, None, None, None, None, 4332.589, 5.204267, 10.97331, 317.8, 0.048775, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Saturn', 7, 'Sun', 1, 14, None, None, None, None, 10759.22, 9.554909, 10.97331659, 95.2, 0.055723219, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Uranus', 8, 'Sun', 1, 14, None, 1781, None, None, 30685.4, 19.22941195, 3.98085, 14.54, 0.044405586, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Neptune', 9, 'Sun', 1, 14, None, 1846, None, None, 60190.03, 30.10366151, 3.8646994, 17.147, 0.011214269, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Pluto', 10, 'Sun', 1, 14, None, 1930, None, None, 90553.02, 39.482117, 0.186517, 0.002, 0.2488273, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Haumea', 11, 'Sun', 1, 14, None, 2004, None, None, 103647.0, 43.28708, 0.12836647, 0.00066, 0.1920504, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Makemake', 12, 'Sun', 1, 14, None, 2005, None, None, 111867.0, 45.436301, 0.11625346, 0.0005, 0.16254481, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Eris', 13, 'Sun', 1, 14, None, 2005, None, None, 203830.0, 67.781, 0.182953687, 0.0027961, 0.44068, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
                     ('Sedna', 14, 'Sun', 1, 14, None, 2003, None, None, 4404480.0, 541.429506, 0.1565252957, 0.000138968, 0.8590486, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ]
    cursor.executemany("INSERT INTO planets VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", solar_planets)

    maxDistance = 0.0
    minDistance = 100000000000000000000000.0
    
    maxPeriod = 0.0
    minPeriod = 100000000000000000000000.0

    with open(filepath) as fp:
        cnt = 15
        for line in fp:
            if line.startswith('#'):
                continue
            
            m = regex.match(line)
            
            pl_name = m.group(1)
            hostname = m.group(2)
            default_flag = m.group(3)
            sy_snum = toInt(m.group(4))
            sy_pnum = toInt(m.group(5))
            discoverymethod = discMethod(m.group(6))
            disc_year = toInt(m.group(7))
            disc_facility = m.group(8)
            pl_refname = m.group(9)
            pl_orbper = toFloat(m.group(10))
            pl_orbsmax = toFloat(m.group(11))
            pl_rade = toFloat(m.group(12))
            pl_bmasse = toFloat(m.group(13))
            pl_orbeccen = toFloat(m.group(14))
            pl_eqt = toFloat(m.group(15))
            st_refname = m.group(16)
            st_spectype = m.group(17)
            st_teff = toFloat(m.group(18))
            st_rad = toFloat(m.group(19))
            st_mass = toFloat(m.group(20))
            sy_refname = m.group(21)
            sy_dist = toFloat(m.group(22))
            rowupdate = m.group(23)
            pl_pubdate = m.group(24)
            releasedate = m.group(25)
            
            row = [(pl_name, cnt, hostname, sy_snum, sy_pnum, discoverymethod, disc_year, disc_facility, pl_refname, pl_orbper, pl_orbsmax, pl_rade, pl_bmasse, pl_orbeccen, pl_eqt, st_refname, st_spectype, st_teff, st_rad, st_mass, sy_refname, sy_dist, rowupdate, pl_pubdate, releasedate)]
            cursor.executemany("INSERT INTO planets VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", row)
            
            cnt += 1
            
            if sy_dist is not None:
                if minDistance > sy_dist:
                    minDistance = sy_dist
                if maxDistance < sy_dist:
                    maxDistance = sy_dist

            if pl_orbper is not None:
                if minPeriod > pl_orbper:
                    minPeriod = pl_orbper
                if maxPeriod < pl_orbper:
                    maxPeriod = pl_orbper

    conn.commit()
    print("minDistance = ", minDistance, "; maxDistance = ", maxDistance)
    print("minPeriod = ", minPeriod, "; maxPeriod = ", maxPeriod)

if __name__ == '__main__':
    main()