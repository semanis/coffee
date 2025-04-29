package com.smanis.coffee.service;

public class ColumnMapping {
   
   private String columnName = null;
   private String translatedColumnName = null;
   private int sortOrder = -1;
   private String dataType = null;

   /**
    * @return the columnName
    */
   public String getColumnName() {
      return columnName;
   }

   /**
    * @param columnName the columnName to set
    */
   public void setColumnName(String columnName) {
      this.columnName = columnName;
      
      // Initially set the translated column name as the "translated" column name, so if you
      // don't want to translate a column name, just don't call setTranslatedColumnName().
      if (this.translatedColumnName == null) {
         this.setTranslatedColumnName(this.columnName);
      }
   }

   /**
    * @return the translatedColumnName
    */
   public String getTranslatedColumnName() {
      return translatedColumnName;
   }

   /**
    * @param translatedColumnName the translatedColumnName to set
    */
   public void setTranslatedColumnName(String translatedColumnName) {
      this.translatedColumnName = translatedColumnName;
   }

   /**
    * @return the sortOrder
    */
   public int getSortOrder() {
      return sortOrder;
   }

   /**
    * @param sortOrder the sortOrder to set
    */
   public void setSortOrder(int sortOrder) {
      this.sortOrder = sortOrder;
   }

   /**
    * @return the dataType
    */
   public String getDataType() {
      return dataType;
   }

   /**
    * @param dataType the dataType to set
    */
   public void setDataType(String dataType) {
      this.dataType = dataType;
   }
   
   
}
