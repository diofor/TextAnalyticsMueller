
/* First created by JCasGen Thu Nov 17 11:13:14 CET 2016 */
package de.unidue.langtech.teaching.pp.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Nov 17 11:13:14 CET 2016
 * @generated */
public class NewType_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NewType.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.type.NewType");
 
  /** @generated */
  final Feature casFeat_countLetterE;
  /** @generated */
  final int     casFeatCode_countLetterE;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getCountLetterE(int addr) {
        if (featOkTst && casFeat_countLetterE == null)
      jcas.throwFeatMissing("countLetterE", "de.unidue.langtech.teaching.pp.type.NewType");
    return ll_cas.ll_getIntValue(addr, casFeatCode_countLetterE);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCountLetterE(int addr, int v) {
        if (featOkTst && casFeat_countLetterE == null)
      jcas.throwFeatMissing("countLetterE", "de.unidue.langtech.teaching.pp.type.NewType");
    ll_cas.ll_setIntValue(addr, casFeatCode_countLetterE, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public NewType_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_countLetterE = jcas.getRequiredFeatureDE(casType, "countLetterE", "uima.cas.Integer", featOkTst);
    casFeatCode_countLetterE  = (null == casFeat_countLetterE) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_countLetterE).getCode();

  }
}



    