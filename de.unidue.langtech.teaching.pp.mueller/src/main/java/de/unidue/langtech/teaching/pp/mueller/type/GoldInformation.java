

/* First created by JCasGen Sat Jan 21 17:15:03 CET 2017 */
package de.unidue.langtech.teaching.pp.mueller.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Feb 01 13:28:12 CET 2017
 * XML source: /Users/JMac/git/TextAnalyticsMueller/de.unidue.langtech.teaching.pp.mueller/src/main/resources/desc/type/GoldInformation.xml
 * @generated */
public class GoldInformation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GoldInformation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected GoldInformation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public GoldInformation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public GoldInformation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public GoldInformation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: Stance

  /** getter for Stance - gets The Stance of the Tweet.
   * @generated
   * @return value of the feature 
   */
  public String getStance() {
    if (GoldInformation_Type.featOkTst && ((GoldInformation_Type)jcasType).casFeat_Stance == null)
      jcasType.jcas.throwFeatMissing("Stance", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GoldInformation_Type)jcasType).casFeatCode_Stance);}
    
  /** setter for Stance - sets The Stance of the Tweet. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setStance(String v) {
    if (GoldInformation_Type.featOkTst && ((GoldInformation_Type)jcasType).casFeat_Stance == null)
      jcasType.jcas.throwFeatMissing("Stance", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((GoldInformation_Type)jcasType).casFeatCode_Stance, v);}    
   
    
  //*--------------*
  //* Feature: Sentiment

  /** getter for Sentiment - gets The Sentiment of the Tweet
   * @generated
   * @return value of the feature 
   */
  public String getSentiment() {
    if (GoldInformation_Type.featOkTst && ((GoldInformation_Type)jcasType).casFeat_Sentiment == null)
      jcasType.jcas.throwFeatMissing("Sentiment", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GoldInformation_Type)jcasType).casFeatCode_Sentiment);}
    
  /** setter for Sentiment - sets The Sentiment of the Tweet 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentiment(String v) {
    if (GoldInformation_Type.featOkTst && ((GoldInformation_Type)jcasType).casFeat_Sentiment == null)
      jcasType.jcas.throwFeatMissing("Sentiment", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((GoldInformation_Type)jcasType).casFeatCode_Sentiment, v);}    
   
    
  //*--------------*
  //* Feature: Opinion

  /** getter for Opinion - gets 
   * @generated
   * @return value of the feature 
   */
  public String getOpinion() {
    if (GoldInformation_Type.featOkTst && ((GoldInformation_Type)jcasType).casFeat_Opinion == null)
      jcasType.jcas.throwFeatMissing("Opinion", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GoldInformation_Type)jcasType).casFeatCode_Opinion);}
    
  /** setter for Opinion - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setOpinion(String v) {
    if (GoldInformation_Type.featOkTst && ((GoldInformation_Type)jcasType).casFeat_Opinion == null)
      jcasType.jcas.throwFeatMissing("Opinion", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((GoldInformation_Type)jcasType).casFeatCode_Opinion, v);}    
    //*--------------*
  //* Feature: Target

  /** getter for Target - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTarget() {
    if (GoldInformation_Type.featOkTst && ((GoldInformation_Type)jcasType).casFeat_Target == null)
      jcasType.jcas.throwFeatMissing("Target", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GoldInformation_Type)jcasType).casFeatCode_Target);}
    
  /** setter for Target - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTarget(String v) {
    if (GoldInformation_Type.featOkTst && ((GoldInformation_Type)jcasType).casFeat_Target == null)
      jcasType.jcas.throwFeatMissing("Target", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((GoldInformation_Type)jcasType).casFeatCode_Target, v);}    
   
    
}

    