#ifndef _PVROI_H_
#define _PVROI_H_

#include "PVCAMIncludes.h"

/***
* User selected region of interest
*/
class PvRoi
{
public:

    /**
    * Creates a new ROI definition from given sensor coordinates.
    * Binning 1x1 is used by default.
    * @param sensorRgnX ROI X position in sensor coordinate system
    * @param sensorRgnY ROI Y position in sensor coordinate system
    * @param sensorRgnWidth ROI width in sensor coordinate system
    * @param sensorRgnHeight ROI height in sensor coordinate system
    */
    PvRoi(uns16 sensorRgnX, uns16 sensorRgnY, uns16 sensorRgnWidth, uns16 sensorRgnHeight) :
        m_sensorRgnX(sensorRgnX), m_sensorRgnY(sensorRgnY),
        m_sensorRgnWidth(sensorRgnWidth), m_sensorRgnHeight(sensorRgnHeight),
        m_binX(1), m_binY(1)
    {
    }
    /**
    * Creates a new ROI definition from given sensor coordinates and binning size.
    * @param sensorRgnX ROI X position in sensor coordinate system
    * @param sensorRgnY ROI Y position in sensor coordinate system
    * @param sensorRgnWidth ROI width in sensor coordinate system
    * @param sensorRgnHeight ROI height in sensor coordinate system
    * @param binX Serial / x-direction binning to be used
    * @param binY Parallel / y-direction binning to be used
    */
    PvRoi(uns16 sensorRgnX, uns16 sensorRgnY, uns16 sensorRgnWidth, uns16 sensorRgnHeight, uns16 binX, uns16 binY) :
        m_sensorRgnX(sensorRgnX), m_sensorRgnY(sensorRgnY),
        m_sensorRgnWidth(sensorRgnWidth), m_sensorRgnHeight(sensorRgnHeight),
        m_binX(binX), m_binY(binY)
    {
    }

    /**
    * Sets the sensor binning factors for both directions without affecting the ROI coordintes
    * @param binX Serial / x-direction binning to be used
    * @param binY Parallel / y-direction binning to be used
    */
    void SetBinning(uns16 binX, uns16 binY)
    {
        m_binX = binX;
        m_binY = binY;
    }
    /**
    * Sets the sensor binning factor for x-direction without affecting the ROI coordintes
    * @param binX Serial / x-direction binning to be used
    */
    void SetBinningX(uns16 binX)
    {
        m_binX = binX;
    }
    /**
    * Sets the sensor binning factor for y-direction without affecting the ROI coordintes
    * @param binY Parallel / y-direction binning to be used
    */
    void SetBinningY(uns16 binY)
    {
        m_binY = binY;
    }

    /**
    * Sets the sensor ROI definition without affecting the binning factors. Please note that
    * all coordinates must be specified in sensor coordinates which is binning agnostic.
    * @param sensorRgnX ROI X position in sensor coordinate system
    * @param sensorRgnY ROI Y position in sensor coordinate system
    * @param sensorRgnWidth ROI width in sensor coordinate system
    * @param sensorRgnHeight ROI height in sensor coordinate system
    */
    void SetSensorRgn(uns16 sensorRgnX, uns16 sensorRgnY, uns16 sensorRgnWidth, uns16 sensorRgnHeight)
    {
        m_sensorRgnX = sensorRgnX;
        m_sensorRgnY = sensorRgnY;
        m_sensorRgnWidth = sensorRgnWidth;
        m_sensorRgnHeight = sensorRgnHeight;
    }

    uns16 BinX() const { return m_binX; }
    uns16 BinY() const { return m_binY; }

    uns16 SensorRgnX() const { return m_sensorRgnX; }
    uns16 SensorRgnY() const { return m_sensorRgnY; }
    uns16 SensorRgnWidth() const { return m_sensorRgnWidth; }
    uns16 SensorRgnHeight() const { return m_sensorRgnHeight; }

    uns16 ImageRgnX() const { return m_sensorRgnX / m_binX; }
    uns16 ImageRgnY() const { return m_sensorRgnY / m_binY; }
    uns16 ImageRgnWidth() const { return m_sensorRgnWidth / m_binX; }
    uns16 ImageRgnHeight() const { return m_sensorRgnHeight / m_binY; }

    rgn_type ToRgnType() const
    {
        rgn_type roi;
        roi.s1 = m_sensorRgnX;
        roi.s2 = m_sensorRgnX + m_sensorRgnWidth - 1;
        roi.sbin = m_binX;
        roi.p1 = m_sensorRgnY;
        roi.p2 = m_sensorRgnY + m_sensorRgnHeight - 1;
        roi.pbin = m_binY;
        return roi;
    }

private:
    uns16 m_sensorRgnX;
    uns16 m_sensorRgnY;
    uns16 m_sensorRgnWidth;
    uns16 m_sensorRgnHeight;

    uns16 m_binX;
    uns16 m_binY;
};

#endif // _PVROI_H_