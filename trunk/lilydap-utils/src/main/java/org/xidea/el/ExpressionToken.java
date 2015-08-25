package org.xidea.el;


/**
 * ��������˵����
 * 0<<12       |   0<<8  |  0<<6  | 0<<2 |  0
 * 1111            1111      11     1111   11      
 * �����������   �������ȼ�     ������־   ���ȼ�  �������
 * 
 * ������־ ˵����
 * 00 һλ������
 * 01 ��λ������
 * 10 ������־(��λ������?)
 * 11 ������־(��λ������?)
 * @author jindw
 */
public abstract interface ExpressionToken {
	public static final int BIT_PRIORITY     = 15<<2;
	public static final int BIT_PRIORITY_SUB = 15<<8;
	public static final int BIT_ARGS         = 3<<6;
	public static final int POS_INC = 12;

	//ֵ���ͣ�<=0��
	//������ǣ�String,Number,Boolean,Null��
//	@Deprecated
//	public static final int VALUE_LAZY = -0x00;
	public static final int VALUE_CONSTANTS = -0x01;//c;
	public static final int VALUE_VAR       = -0x02;//n;
	public static final int VALUE_LIST      = -0x03;//[;
	public static final int VALUE_MAP       = -0x04;//{;
	
	
	//�ţ�����߼����������ţ�
	public static final int OP_GET      = 0<<12 | 0<<8 | 1<<6 | 8<<2 | 0;
	public static final int OP_INVOKE   = 0<<12 | 0<<8 | 1<<6 | 8<<2 | 1;
	
	//��
	public static final int OP_NOT     = 0<<12 | 0<<8 | 0<<6 | 7<<2 | 0;
	public static final int OP_BIT_NOT = 0<<12 | 0<<8 | 0<<6 | 7<<2 | 1;
	public static final int OP_POS     = 0<<12 | 0<<8 | 0<<6 | 7<<2 | 2;
	public static final int OP_NEG     = 0<<12 | 0<<8 | 0<<6 | 7<<2 | 3;
	
	//�ߣ�
	public static final int OP_MUL = 0<<12 | 0<<8 | 1<<6 | 6<<2 | 0;
	public static final int OP_DIV = 0<<12 | 0<<8 | 1<<6 | 6<<2 | 1;
	public static final int OP_MOD = 0<<12 | 0<<8 | 1<<6 | 6<<2 | 2;
	
	//����
	//���������Ź���������ֵ
	public static final int OP_ADD = 0<<12 | 0<<8 | 1<<6 | 5<<2 | 0;
	public static final int OP_SUB = 0<<12 | 0<<8 | 1<<6 | 5<<2 | 1;
	

	//��
	public static final int OP_LT   =  0<<12 | 1<<8 | 1<<6 | 4<<2 | 0;
	public static final int OP_GT   =  0<<12 | 1<<8 | 1<<6 | 4<<2 | 1;
	public static final int OP_LTEQ =  0<<12 | 1<<8 | 1<<6 | 4<<2 | 2;
	public static final int OP_GTEQ =  0<<12 | 1<<8 | 1<<6 | 4<<2 | 3;
	public static final int OP_IN   =  1<<12 | 1<<8 | 1<<6 | 4<<2 | 0;
	
	public static final int OP_EQ        =  0<<12 | 0<<8 | 1<<6 | 4<<2 | 0;
	public static final int OP_NE        =  0<<12 | 0<<8 | 1<<6 | 4<<2 | 1;
	public static final int OP_EQ_STRICT =  0<<12 | 0<<8 | 1<<6 | 4<<2 | 2;
	public static final int OP_NE_STRICT =  0<<12 | 0<<8 | 1<<6 | 4<<2 | 3;
	
	//��:
	public static final int OP_BIT_AND = 0<<12 | 2<<8 | 1<<6 | 3<<2 | 0;
	public static final int OP_BIT_XOR = 0<<12 | 1<<8 | 1<<6 | 3<<2 | 0;
	public static final int OP_BIT_OR  = 0<<12 | 0<<8 | 1<<6 | 3<<2 | 0;
	
	
	//����
	public static final int OP_AND = 0<<12 | 1<<8 | 1<<6 | 2<<2 | 0;
	public static final int OP_OR  = 0<<12 | 0<<8 | 1<<6 | 2<<2 | 0;

	//����
	//?;
	public static final int OP_QUESTION        = 0<<12 | 0<<8 | 1<<6 | 1<<2 | 0;
	//:;
	public static final int OP_QUESTION_SELECT = 0<<12 | 0<<8 | 1<<6 | 1<<2 | 1;
	
	//һ��
	//��Map Join ������������map join ����ԣ�
	public static final int OP_JOIN   = 0<<12 | 0<<8 | 1<<6 | 0<<2 | 0;
	//����Ԫ�������������ֵ
	public static final int OP_PUSH   = 0<<12 | 0<<8 | 1<<6 | 0<<2 | 1;
	

	
	
	public int getType();
	public String toString();
	public ExpressionToken getLeft();
	public ExpressionToken getRight();
	public Object getParam();
}
