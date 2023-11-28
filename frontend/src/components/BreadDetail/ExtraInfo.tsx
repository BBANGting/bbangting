import { Box, Tab, Tabs } from '@mui/material';
import { SyntheticEvent, useState } from 'react';
import TabPanel from './TabPanel';
import BreadInfo from './BreadInfo';
import BreadReview from './BreadReview';

const ExtraInfo: React.FC = () => {
  const [value, setValue] = useState<number>(0);

  const handleChange = (e: SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };
  return (
    <Box mt={10}>
      <Tabs
        onChange={handleChange}
        value={value}
        textColor="inherit"
        indicatorColor="secondary"
        variant="fullWidth"
      >
        <Tab label="상세 정보" />
        <Tab label="리뷰" />
        <Tab label="문의" />
      </Tabs>
      <Box>
        <TabPanel value={value} index={0}>
          <BreadInfo />
        </TabPanel>
        <TabPanel value={value} index={1}>
          <BreadReview />
        </TabPanel>
      </Box>
    </Box>
  );
};

export default ExtraInfo;
