

/* First created by JCasGen Thu Jan 26 11:15:57 CET 2017 */
package de.unidue.langtech.teaching.pp.mueller.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.LongArray;
import org.apache.uima.jcas.tcas.Annotation;


import org.apache.uima.jcas.cas.IntegerArray;


/** 
 * Updated by JCasGen Mon Jan 30 22:03:09 CET 2017
 * XML source: /home/jonas/git/TextAnalyticsMueller/de.unidue.langtech.teaching.pp.mueller/src/main/resources/desc/type/DetectedInformation.xml
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
   
    
  //*--------------*
  //* Feature: sentiment_value_wordnet

  /** getter for sentiment_value_wordnet - gets 
   * @generated
   * @return value of the feature 
   */
  public double getSentiment_value_wordnet() {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sentiment_value_wordnet == null)
      jcasType.jcas.throwFeatMissing("sentiment_value_wordnet", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sentiment_value_wordnet);}
    
  /** setter for sentiment_value_wordnet - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentiment_value_wordnet(double v) {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sentiment_value_wordnet == null)
      jcasType.jcas.throwFeatMissing("sentiment_value_wordnet", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sentiment_value_wordnet, v);}    
   
    
  //*--------------*
  //* Feature: sent_count_pos

  /** getter for sent_count_pos - gets 
   * @generated
   * @return value of the feature 
   */
  public long getSent_count_pos() {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sent_count_pos == null)
      jcasType.jcas.throwFeatMissing("sent_count_pos", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return jcasType.ll_cas.ll_getLongValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sent_count_pos);}
    
  /** setter for sent_count_pos - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSent_count_pos(long v) {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sent_count_pos == null)
      jcasType.jcas.throwFeatMissing("sent_count_pos", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    jcasType.ll_cas.ll_setLongValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sent_count_pos, v);}    
   
    
  //*--------------*
  //* Feature: sent_count_neg

  /** getter for sent_count_neg - gets 
   * @generated
   * @return value of the feature 
   */
  public long getSent_count_neg() {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sent_count_neg == null)
      jcasType.jcas.throwFeatMissing("sent_count_neg", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return jcasType.ll_cas.ll_getLongValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sent_count_neg);}
    
  /** setter for sent_count_neg - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSent_count_neg(long v) {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sent_count_neg == null)
      jcasType.jcas.throwFeatMissing("sent_count_neg", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    jcasType.ll_cas.ll_setLongValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sent_count_neg, v);}    
   
    
  //*--------------*
  //* Feature: sent_count_other

  /** getter for sent_count_other - gets 
   * @generated
   * @return value of the feature 
   */
  public long getSent_count_other() {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sent_count_other == null)
      jcasType.jcas.throwFeatMissing("sent_count_other", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return jcasType.ll_cas.ll_getLongValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sent_count_other);}
    
  /** setter for sent_count_other - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSent_count_other(long v) {
    if (DetectedInformation_Type.featOkTst && ((DetectedInformation_Type)jcasType).casFeat_sent_count_other == null)
      jcasType.jcas.throwFeatMissing("sent_count_other", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    jcasType.ll_cas.ll_setLongValue(addr, ((DetectedInformation_Type)jcasType).casFeatCode_sent_count_other, v);}    
  }

    