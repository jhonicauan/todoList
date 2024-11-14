package com.jhoni.todoList.Utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

public class Utils {

    public static void copiarPropriedades(Object source,Object target){
        BeanUtils.copyProperties(source, target,obterValoresNulos(source));
    }
    
    public static String[] obterValoresNulos(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> nomesVazios = new HashSet<>();

        for(PropertyDescriptor pd:pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            
            if(srcValue == null){
                nomesVazios.add(pd.getName());
            }
        }

        String[] resultado = new String[nomesVazios.size()];
        return nomesVazios.toArray(resultado);
    }
}
