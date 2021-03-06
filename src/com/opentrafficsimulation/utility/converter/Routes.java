//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.12 at 12:09:05 PM IST 
//


package com.opentrafficsimulation.utility.converter;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="vehicle" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="route">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="edges" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                 &lt;attribute name="depart" type="{http://www.w3.org/2001/XMLSchema}float" />
 *                 &lt;attribute name="maxSpeed" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                 &lt;attribute name="vehicleType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vehicle"
})
@XmlRootElement(name = "routes")
public class Routes {

    protected List<Routes.Vehicle> vehicle;

    /**
     * Gets the value of the vehicle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vehicle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVehicle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Routes.Vehicle }
     * 
     * 
     */
    public List<Routes.Vehicle> getVehicle() {
        if (vehicle == null) {
            vehicle = new ArrayList<Routes.Vehicle>();
        }
        return this.vehicle;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="route">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="edges" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *       &lt;attribute name="depart" type="{http://www.w3.org/2001/XMLSchema}float" />
     *       &lt;attribute name="maxSpeed" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *       &lt;attribute name="vehicleType" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "route"
    })
    public static class Vehicle {

        @XmlElement(required = true)
        protected Routes.Vehicle.Route route;
        @XmlAttribute
        protected String id;
        @XmlAttribute
        protected String depart;
        @XmlAttribute
        protected String maxSpeed;
        @XmlAttribute(name="vType")
        protected String vehicleType;
        @XmlAttribute(name="color")
        private String color;
        @XmlAttribute(name="guiShape")
        private String guiShape;

        /**
         * Gets the value of the route property.
         * 
         * @return
         *     possible object is
         *     {@link Routes.Vehicle.Route }
         *     
         */
        public Routes.Vehicle.Route getRoute() {
            return route;
        }

        /**
         * Sets the value of the route property.
         * 
         * @param value
         *     allowed object is
         *     {@link Routes.Vehicle.Route }
         *     
         */
        public void setRoute(Routes.Vehicle.Route value) {
            this.route = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the depart property.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public String getDepart() {
            return depart;
        }

        /**
         * Sets the value of the depart property.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setDepart(String value) {
            this.depart = value;
        }

        /**
         * Gets the value of the maxSpeed property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public String getMaxSpeed() {
            return maxSpeed;
        }

        /**
         * Sets the value of the maxSpeed property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setMaxSpeed(String value) {
            this.maxSpeed = value;
        }

        /**
         * Gets the value of the vehicleType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVehicleType() {
            return vehicleType;
        }

        /**
         * Sets the value of the vehicleType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVehicleType(String value) {
            this.vehicleType = value;
        }

        /**
         * @return the color
         */
        public String getColor() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(String color) {
            this.color = color;
        }

        /**
         * @return the guiShape
         */
        public String getGuiShape() {
            return guiShape;
        }

        /**
         * @param guiShape the guiShape to set
         */
        public void setGuiShape(String guiShape) {
            this.guiShape = guiShape;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="edges" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Route {

            @XmlValue
            protected String value;
            @XmlAttribute
            protected String edges;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the edges property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEdges() {
                return edges;
            }

            /**
             * Sets the value of the edges property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEdges(String value) {
                this.edges = value;
            }

        }

    }

}
