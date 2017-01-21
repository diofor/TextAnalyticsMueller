
/* First created by JCasGen Sat Jan 21 17:15:03 CET 2017 */
package de.unidue.langtech.teaching.pp.mueller.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sat Jan 21 17:15:03 CET 2017
 * @generated */
public class GoldInformation_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GoldInformation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
 
  /** @generated */
  final Feature casFeat_TargetText;
  /** @generated */
  final int     casFeatCode_TargetText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTargetText(int addr) {
        if (featOkTst && casFeat_TargetText == null)
      jcas.throwFeatMissing("TargetText", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_TargetText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTargetText(int addr, String v) {
        if (featOkTst && casFeat_TargetText == null)
      jcas.throwFeatMissing("TargetText", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    ll_cas.ll_setStringValue(addr, casFeatCode_TargetText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Stance;
  /** @generated */
  final int     casFeatCode_Stance;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getStance(int addr) {
        if (featOkTst && casFeat_Stance == null)
      jcas.throwFeatMissing("Stance", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Stance);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setStance(int addr, String v) {
        if (featOkTst && casFeat_Stance == null)
      jcas.throwFeatMissing("Stance", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Stance, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Sentiment;
  /** @generated */
  final int     casFeatCode_Sentiment;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSentiment(int addr) {
        if (featOkTst && casFeat_Sentiment == null)
      jcas.throwFeatMissing("Sentiment", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Sentiment);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentiment(int addr, String v) {
        if (featOkTst && casFeat_Sentiment == null)
      jcas.throwFeatMissing("Sentiment", "de.unidue.langtech.teaching.pp.mueller.type.GoldInformation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Sentiment, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GoldInformation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_TargetText = jcas.getRequiredFeatureDE(casType, "TargetText", "uima.cas.String", featOkTst);
    casFeatCode_TargetText  = (null == casFeat_TargetText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_TargetText).getCode();

 
    casFeat_Stance = jcas.getRequiredFeatureDE(casType, "Stance", "uima.cas.String", featOkTst);
    casFeatCode_Stance  = (null == casFeat_Stance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Stance).getCode();

 
    casFeat_Sentiment = jcas.getRequiredFeatureDE(casType, "Sentiment", "uima.cas.String", featOkTst);
    casFeatCode_Sentiment  = (null == casFeat_Sentiment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Sentiment).getCode();

  }
}



    