

/* First created by JCasGen Thu Jan 26 11:15:57 CET 2017 */
package de.unidue.langtech.teaching.pp.mueller.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Jan 26 11:15:57 CET 2017
 * XML source: /Users/JMac/git/TextAnalyticsMueller/de.unidue.langtech.teaching.pp.mueller/src/main/resources/desc/type/DetectedInformation.xml
 * @generated */
public class DetectedInformation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DetectedInformation.class);
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
  protected DetectedInformation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DetectedInformation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DetectedInformation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DetectedInformation(JCas jcas, int begin, int end) {
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
  //* Feature: target

  /** getter for target - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTarget() {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_target == null)
      jcasType.jcas.throwFeatMissing("target", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_target);}
    
  /** setter for target - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTarget(String v) {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_target == null)
      jcasType.jcas.throwFeatMissing("target", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_target, v);}    
   
    
  //*--------------*
  //* Feature: stance

  /** getter for stance - gets 
   * @generated
   * @return value of the feature 
   */
  public String getStance() {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_stance == null)
      jcasType.jcas.throwFeatMissing("stance", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_stance);}
    
  /** setter for stance - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setStance(String v) {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_stance == null)
      jcasType.jcas.throwFeatMissing("stance", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_stance, v);}    
   
    
  //*--------------*
  //* Feature: sentiment

  /** getter for sentiment - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSentiment() {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sentiment == null)
      jcasType.jcas.throwFeatMissing("sentiment", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sentiment);}
    
  /** setter for sentiment - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentiment(String v) {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sentiment == null)
      jcasType.jcas.throwFeatMissing("sentiment", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sentiment, v);}    
   
    
  //*--------------*
  //* Feature: opinion

  /** getter for opinion - gets 
   * @generated
   * @return value of the feature 
   */
  public String getOpinion() {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_opinion == null)
      jcasType.jcas.throwFeatMissing("opinion", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_opinion);}
    
  /** setter for opinion - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setOpinion(String v) {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_opinion == null)
      jcasType.jcas.throwFeatMissing("opinion", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_opinion, v);}    
  }

    