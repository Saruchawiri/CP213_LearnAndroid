# Mobile Application: Decidr 

⸻

## Branding Concept
- **App Name:** Decidr
- **Creator:** sarucha
- **Mascot:** Jelly (Golden Retriever 🐶)
- **Concept:** “เพื่อนอัจฉริยะที่ช่วยคุณตัดสินใจ”

⸻

## Step 1: Project Overview

### 1. ชื่อโครงการ
**Decidr by sarucha**  
AI-powered Decision Support Mobile Application

### 2. ที่มาและความสำคัญของโครงการ
ในชีวิตประจำวัน ผู้คนต้องเผชิญกับการตัดสินใจอยู่เสมอ เช่นควรซื้อของหรือไม่ ควรเลือกทำงานชิ้นใดก่อน หรือควรเลือกตัวเลือกใดในหลายทางเลือก

แม้จะเป็นการตัดสินใจทั่วไป แต่เมื่อมีหลายปัจจัย เช่น ราคา เวลา ความคุ้มค่า และความรู้สึกส่วนตัว เข้ามาเกี่ยวข้อง มักทำให้เกิดความลังเล มีอาการตัดสินใจไม่ได้ (Decision Fatigue) และใช้เวลานาน

แอป Decidr ถูกพัฒนาขึ้นเพื่อแก้ปัญหาดังกล่าว โดยใช้แนวคิด:
- **AI-assisted decision making**
- **Weighted scoring system**
- **Explainable AI**

ร่วมกับการออกแบบ UX ที่เป็นมิตร โดยมีตัวละคร Jelly (สุนัขโกลเด้น) เป็นผู้ช่วยในการสื่อสารกับผู้ใช้ ทำให้แอปนี้ไม่ใช่เพียงเครื่องมือช่วยเลือก แต่เป็น **Intelligent & Personalized Decision Companion**

### 3. วัตถุประสงค์
- พัฒนาแอป Android สำหรับช่วยตัดสินใจด้วยพลังของ AI
- ฝึกใช้ออกแบบ UI ด้วย Kotlin + Jetpack Compose
- ออกแบบระบบ Decision Engine ในการคิดคำนวณและประเมินผล
- เรียนรู้และใช้งาน Clean Architecture ร่วมกับรูปแบบ MVVM
- พัฒนา Explainable AI (AI ที่อธิบายเหตุผลได้) และระบบ Feedback Loop
- สร้างแอปที่มี UX/UI ใช้งานง่าย มีการนำเสนอตัวละครและ Branding ที่ชัดเจน

### 4. กลุ่มเป้าหมาย
- นักศึกษา
- คนวัยทำงาน (18–35 ปี)
- คนที่ตัดสินใจยาก (มีปัญหาเจอมักลังเลรักพี่เสียดายน้อง)
- ผู้ที่สนใจเทคโนโลยี AI และ Productivity tools

⸻

## Step 2: Tech Stack

- **Platform:** Android (Kotlin) / Android Studio
- **UI:** Jetpack Compose, Material 3, Native Animations
- **Architecture:** MVVM, Clean Architecture (Presentation, Domain, Data)
- **Database:** Room Database (Offline-first local storage)
- **AI:** Google Gemini 2.0 Flash API (via Retrofit & OkHttp)
- **Design:** Pastel UI + Mascot-driven UX (น้องหมา Jelly)

⸻

## Step 3: System Design

### Core Components
1. **Decision Engine:** คำนวณแบบ Weighted Scoring ประเมินผลตาม Factor
2. **AI Reasoning Engine:** วิเคราะห์ข้อดี/ข้อเสีย (pros/cons) พร้อมให้คำแนะนำและอธิบายเหตุผล
3. **Feedback Loop:** User สามารถกด 👍 / 👎 เพื่อให้ข้อมูลสะท้อนกลับได้
4. **Jelly Assistant:** แสดงข้อความคอยไกด์ User สร้างความน่ารัก เพิ่ม Personality ให้ AI

### Screen Structure
- **Main Screen:** ใส่คำถาม, เพิ่มตัวเลือก, เพิ่ม Factor + Weight (รองรับ Advanced Mode)
- **Result Screen:** แสดงคะแนน, Highlight ตัวเลือกที่ดีที่สุด, แจกแจงข้อดีข้อเสีย และคำแนะนำจาก Jelly
- **Chat Screen:** สามารถพิมพ์คุยถาม AI ถกเถียงเรื่องเหตุผลต่อแบบ Context-aware
- **History Screen:** ดูผลการตัดสินใจย้อนหลัง

### System Flow
1. ผู้ใช้สร้างคำถามหรือเรื่องที่ต้องการตัดสินใจ
2. ป้อนข้อมูลตัวเลือกต่างๆ 
3. เลือก Mode เข้าสู่ขั้นสูง (Advanced) เพื่อระบุปัจจัยพร้อมน้ำหนักถ้าต้องการ
4. ระบบส่งข้อมูลให้ AI วิเคราะห์ และคำนวณคะแนน
5. แสดงผลลัพธ์แบบเจาะลึก พร้อมข้อความแนะนำจาก Jelly
6. User สามารถแชทถามแบบเจาะลึกเพิ่ม หรือให้พึงพอใจ Feedback (กด Like/Dislike)
7. ระบบบันทึกข้อมูลและประวัติการใช้งานลง Local Database (History)

### Data Structure (Database Entities)
- **Decision:** id, query, options, factors, created_at
- **Option:** id, decision_id, title, score, pros, cons
- **Factor:** id, decision_id, name, weight
- **History:** บันทึกโครงสร้างการผลการวิเคราะห์

⸻

## Step 4: Functional Requirements

- **Core:** สามารถเพิ่มตัวเลือก, เพิ่ม factor + การตั้งค่าน้ำหนัก, และระบบคำนวณคะแนนการตัดสินใจเบื้องต้น
- **AI:** ให้ AI วิเคราะห์เปรียบเทียบข้อดี/ข้อเสีย, แนะนำตัวเลือกที่เป็นผู้ชนะ และอธิบายเหตุผลอ้างอิง
- **Advanced:** มีสวิตช์เปิด-ปิด โหมด Advanced, ฟีเจอร์ Chat follow-up หลังการวิเคราะห์ และระบบ Feedback ลูป
- **UX:** สื่อสารกับผู้ใช้งานผ่าน Jelly assistant, ให้ข้อมูลเข้าใจง่าย

⸻

## Step 5: Non-Functional Requirements
- **Usability:** ใช้งานง่าย UX ไม่ซับซ้อน
- **Performance:** ตอบสนองเร็ว โหลดผลจาก AI ได้อย่างลื่นไหล
- **Aesthetic:** UI น่ารัก สีพาสเทล ทันสมัย ดึงดูดสายตา
- **Reliability:** รองรับการจัดการเคส Error ยกตัวอย่างเช่น Rate Limit ติดขัดได้โดยแอปไม่เด้ง
- **Scalability:** สามารถนำไปต่อยอดเพิ่มฟีเจอร์อื่นๆ หรือขยายสเกลระบบต่อได้ง่าย

⸻

## Step 6: Expected Outcomes
- ได้แอปพลิเคชันรูปแบบ Android ที่นำไปใช้งานได้จริง
- ปลดล็อกความเข้าใจการเชื่อมต่อ AI Integration ขั้นสูง (ผ่าน JSON แตกเป็น Object)
- ได้ Portfolio ระดับสูง และผลงานชิ้นเอก 30% ของรายวิชาที่มี Wow Factor
- แสดงความสามารถด้าน Product Thinking & UX/UI Design
