package com.gxa.jbgsw.business.listener;

import com.gxa.jbgsw.common.utils.ComputeSimilarityRatio;
import com.gxa.jbgsw.common.utils.StrCommon;

/**
 * @Author Mr. huang
 * @Date 2023/9/28 0028 15:03
 * @Version 2.0
 */
public class Test {

    public static void main(String[] args) {
        String val = "纳米材料新能源领域新型光伏光电材料和器件严克友华南理工大学环境与能源学院教授博士生导师“兴华精英学者” 入选者。担任JACS、Nature Communications等权威期刊的审稿人是纳米材料和新能源领域的青年专家。严教授为美国化学会(ACS)会员美国材料学会(MRS) 和电机电子工程师学会(EEE)会员。\n" +
                "\n" +
                "一直从事新型光伏光电材料和器件的研究在国际期刊上发表了60余篇学术论文包括Journal of the American Chemistry Society(JACS)、Nature CommunicationsACS NanoEnergy & Environmental Science等文章他弓|4000多次其中8篇论文入选ESI高引论文成果多次被学术媒体予以亮点报道和专题评述。受邀在国际学术会议做主题报告邀请报告多项如UCL A的AFM会议主持并作邀请报告等。主持国家地区人才项目等超过千万元作为成员获得教育部自然科学二等奖等。";
        String a = "畜牧农业生态环境技术";

        String sameWords = ComputeSimilarityRatio.longestCommonSubstringNoOrder(val, a);
        System.out.println(sameWords);
    }

}
