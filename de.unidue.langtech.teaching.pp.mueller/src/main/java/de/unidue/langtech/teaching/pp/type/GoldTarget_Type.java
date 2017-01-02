
/* First created by JCasGen Thu Dec 15 12:42:00 CET 2016 */
package de.unidue.langtech.teaching.pp.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Dec 15 13:04:55 CET 2016
 * @generated */
public class GoldTarget_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GoldTarget.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.type.GoldTarget");



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
      jcas.throwFeatMissing("TargetText", "de.unidue.langtech.teaching.pp.type.GoldTarget");
    return ll_cas.ll_getStringValue(addr, casFeatCode_TargetText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTargetText(int addr, String v) {
        if (featOkTst && casFeat_TargetText == null)
      jcas.throwFeatMissing("TargetText", "de.unidue.langtech.teaching.pp.type.GoldTarget");
    ll_cas.ll_setStringValue(addr, casFeatCode_TargetText, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GoldTarget_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_TargetText = jcas.getRequiredFeatureDE(casType, "TargetText", "uima.cas.String", featOkTst);
    casFeatCode_TargetText  = (null == casFeat_TargetText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_TargetText).getCode();

  }
}



    