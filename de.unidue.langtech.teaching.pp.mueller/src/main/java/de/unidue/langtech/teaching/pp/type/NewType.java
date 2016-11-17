

/* First created by JCasGen Thu Nov 17 11:13:14 CET 2016 */
package de.unidue.langtech.teaching.pp.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Nov 17 11:13:14 CET 2016
 * XML source: /Users/JMac/git/TextAnalyticsMueller/de.unidue.langtech.teaching.pp.mueller/src/main/resources/desc/type/NewType.xml
 * @generated */
public class NewType extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NewType.class);
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
  protected NewType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NewType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NewType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public NewType(JCas jcas, int begin, int end) {
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
  //* Feature: countLetterE

  /** getter for countLetterE - gets 
   * @generated
   * @return value of the feature 
   */
  public int getCountLetterE() {
    if (NewType_Type.featOkTst && ((NewType_Type)jcasType).casFeat_countLetterE == null)
      jcasType.jcas.throwFeatMissing("countLetterE", "de.unidue.langtech.teaching.pp.type.NewType");
    return jcasType.ll_cas.ll_getIntValue(addr, ((NewType_Type)jcasType).casFeatCode_countLetterE);}
    
  /** setter for countLetterE - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCountLetterE(int v) {
    if (NewType_Type.featOkTst && ((NewType_Type)jcasType).casFeat_countLetterE == null)
      jcasType.jcas.throwFeatMissing("countLetterE", "de.unidue.langtech.teaching.pp.type.NewType");
    jcasType.ll_cas.ll_setIntValue(addr, ((NewType_Type)jcasType).casFeatCode_countLetterE, v);}    
  }

    