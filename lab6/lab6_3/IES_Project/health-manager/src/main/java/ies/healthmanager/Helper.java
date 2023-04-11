package ies.healthmanager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Helper {
    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static Object getLastElement(Collection c) {
        Iterator itr = c.iterator();
        if (!itr.hasNext())
            return null;
        Object lastElement = itr.next();
        while (itr.hasNext())
            lastElement = itr.next();
        return lastElement;
    }

    public static Query getQueryOfHealthAttributeWithParams(EntityManager entityManager, Class concreteHealthAttr,
            Map<String, String> params) {
        String strQuery = "SELECT elem FROM " + concreteHealthAttr.getName() + " elem";
        Query query = null;

        // First, gather WHERE conditions

        ArrayList<String> whereConditions = new ArrayList<String>();

        LocalDateTime start = null;
        if (params.containsKey("startDateTime") && !params.get("startDateTime").isBlank()) {
            String startDateTime = params.get("startDateTime").replace("T", " ");
            String pattern = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            start = LocalDateTime.parse(startDateTime, formatter);
            whereConditions.add("elem.dateTime >= :startDateTime");
        }

        LocalDateTime end = null;
        if (params.containsKey("endDateTime") && !params.get("endDateTime").isBlank()) {
            String endDateTime = params.get("endDateTime").replace("T", " ");
            String pattern = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            end = LocalDateTime.parse(endDateTime, formatter);
            whereConditions.add("elem.dateTime <= :endDateTime");
        }

        if (params.containsKey("patientId") && !params.get("patientId").isBlank() )
        {
            int patientId = Integer.parseInt(params.get("patientId"));
            whereConditions.add("elem.patient.id = " + patientId);
        }

        // Then gather ORDER BY conditions

        ArrayList<String> orderByConditions = new ArrayList<String>(); // "age DESC" or "firstName" for example
        if (params.containsKey("sortBy") && !params.get("sortBy").isBlank()) {
            String str = params.get("sortBy");
            // sortBy=-dateTime,heartRate,-patientId
            String[] splitted = str.split(",");
            // SELECT * FROM emp_salary ORDER BY age ASC, salary DESC
            for (String field : splitted) {
                // x == "-dateTime"
                if (field.charAt(0) == '-')
                    orderByConditions.add(field.substring(1, field.length()) + " DESC");
                else
                    orderByConditions.add(field);
            }
        }

        // Add where conditions to str
        Iterator<String> iter = whereConditions.iterator();
        if (iter.hasNext())
            strQuery += " WHERE " + iter.next();
        while (iter.hasNext())
            strQuery += " AND " + iter.next();
        // Add order by conditions str
        iter = orderByConditions.iterator();
        if (iter.hasNext())
            strQuery += " ORDER BY " + iter.next();
        while (iter.hasNext())
            strQuery += ", " + iter.next();
        query = entityManager.createQuery(strQuery);
        // Set where conditions parameters
        if (start != null)
            query = query.setParameter("startDateTime", start);
        if (end != null)
            query = query.setParameter("endDateTime", end);

        // Then, treat LIMIT clause

        int limit = -1;
        if (params.containsKey("limit") && !params.get("limit").isBlank()) {
            limit = Integer.parseInt(params.get("limit"));
            query.setMaxResults(limit);
        }

        return query;
    }

}