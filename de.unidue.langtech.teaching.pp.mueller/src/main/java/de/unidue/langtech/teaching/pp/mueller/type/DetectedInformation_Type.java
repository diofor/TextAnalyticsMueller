
/* First created by JCasGen Thu Jan 26 11:15:57 CET 2017 */
package de.unidue.langtech.teaching.pp.mueller.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Jan 26 11:15:57 CET 2017
 * @generated */
public class DetectedInformation_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DetectedInformation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
 
  /** @generated */
  final Feature casFeat_target;
  /** @generated */
  final int     casFeatCode_target;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTarget(int addr) {
        if (featOkTst && casFeat_target == null)
      jcas.throwFeatMissing("target", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_target);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTarget(int addr, String v) {
        if (featOkTst && casFeat_target == null)
      jcas.throwFeatMissing("target", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    ll_cas.ll_setStringValue(addr, casFeatCode_target, v);}
    
  
 
  /** @generated */
  final Feature casFeat_stance;
  /** @generated */
  final int     casFeatCode_stance;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getStance(int addr) {
        if (featOkTst && casFeat_stance == null)
      jcas.throwFeatMissing("stance", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_stance);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setStance(int addr, String v) {
        if (featOkTst && casFeat_stance == null)
      jcas.throwFeatMissing("stance", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    ll_cas.ll_setStringValue(addr, casFeatCode_stance, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentiment;
  /** @generated */
  final int     casFeatCode_sentiment;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSentiment(int addr) {
        if (featOkTst && casFeat_sentiment == null)
      jcas.throwFeatMissing("sentiment", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sentiment);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentiment(int addr, String v) {
        if (featOkTst && casFeat_sentiment == null)
      jcas.throwFeatMissing("sentiment", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    ll_cas.ll_setStringValue(addr, casFeatCode_sentiment, v);}
    
  
 
  /** @generated */
  final Feature casFeat_opinion;
  /** @generated */
  final int     casFeatCode_opinion;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOpinion(int addr) {
        if (featOkTst && casFeat_opinion == null)
      jcas.throwFeatMissing("opinion", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_opinion);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOpinion(int addr, String v) {
        if (featOkTst && casFeat_opinion == null)
      jcas.throwFeatMissing("opinion", "de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation");
    ll_cas.ll_setStringValue(addr, casFeatCode_opinion, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DetectedInformation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_target = jcas.getRequiredFeatureDE(casType, "target", "uima.cas.String", featOkTst);
    casFeatCode_target  = (null == casFeat_target) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_target).getCode();

 
    casFeat_stance = jcas.getRequiredFeatureDE(casType, "stance", "uima.cas.String", featOkTst);
    casFeatCode_stance  = (null == casFeat_stance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_stance).getCode();

 
    casFeat_sentiment = jcas.getRequiredFeatureDE(casType, "sentiment", "uima.cas.String", featOkTst);
    casFeatCode_sentiment  = (null == casFeat_sentiment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentiment).getCode();

 
    casFeat_opinion = jcas.getRequiredFeatureDE(casType, "opinion", "uima.cas.String", featOkTst);
    casFeatCode_opinion  = (null == casFeat_opinion) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_opinion).getCode();

  }
}



    