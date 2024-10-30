CREATE TABLE MarsWeatherData (
    id VARCHAR(24) PRIMARY KEY,
    firstUTC DATETIME,
    lastUTC DATETIME,
    monthOrdinal INT,
    northernSeason VARCHAR(20),
    southernSeason VARCHAR(20),
    season VARCHAR(20),
    PRE JSON,
    AT JSON,
    HWS JSON,
    WD JSON
);
